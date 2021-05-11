package net.fireplacemode.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.api.Environment;
import net.fireplacemode.FireplaceMain;
import net.fabricmc.api.EnvType;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {

    public GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "initWidgets", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/MinecraftClient;isIntegratedServerRunning()Z"), cancellable = true)
    private void initWidgetsMixin(CallbackInfo info) {
        if (this.client.player != null && !this.client.player.isCreative() && !this.client.player.isSpectator()
                && this.client.player.world.getGameRules().getBoolean(FireplaceMain.FIREPLACE_MODE)) {
            int heatingRange = 3;
            boolean isCampfireNear = false;
            for (int i = -heatingRange; i < heatingRange + 1; i++) {
                for (int u = -heatingRange; u < heatingRange + 1; u++) {
                    for (int k = -heatingRange - 1; k < heatingRange; k++) {
                        BlockPos pos = new BlockPos(this.client.player.getBlockPos().getX() + i,
                                this.client.player.getBlockPos().getY() + k,
                                this.client.player.getBlockPos().getZ() + u);
                        if (this.client.player.world.getBlockState(pos).isIn(FireplaceMain.SAVE_BLOCK)) {
                            isCampfireNear = true;
                            break;
                        }
                    }
                }
            }
            if (!isCampfireNear) {
                info.cancel();
            }
        }
    }

}
