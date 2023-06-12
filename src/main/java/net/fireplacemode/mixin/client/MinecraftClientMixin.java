package net.fireplacemode.mixin.client;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.api.Environment;
import net.fireplacemode.FireplaceMain;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Shadow
    private boolean paused;

    @Shadow
    private String getWindowTitle() {
        return null;
    }

    @ModifyVariable(method = "Lnet/minecraft/client/MinecraftClient;render(Z)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;paused:Z", ordinal = 1), ordinal = 1)
    private boolean renderMixin(boolean original) {
        if (player != null) {
            if (this.paused != original && !player.isCreative() && !player.isSpectator() && this.player.getWorld().getGameRules().getBoolean(FireplaceMain.FIREPLACE_MODE)) {
                int heatingRange = 3;
                boolean isCampfireNear = false;
                for (int i = -heatingRange; i < heatingRange + 1; i++) {
                    for (int u = -heatingRange; u < heatingRange + 1; u++) {
                        for (int k = -heatingRange - 1; k < heatingRange; k++) {
                            BlockPos pos = new BlockPos(player.getBlockPos().getX() + i, player.getBlockPos().getY() + k, player.getBlockPos().getZ() + u);
                            if (player.getWorld().getBlockState(pos).isIn(FireplaceMain.SAVE_BLOCK)) {
                                isCampfireNear = true;
                                break;
                            }
                        }
                    }
                }
                if (!isCampfireNear) {
                    return false;
                }
            }
        }
        return original;
    }

}
