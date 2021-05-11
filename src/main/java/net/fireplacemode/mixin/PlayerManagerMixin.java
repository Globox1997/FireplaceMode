package net.fireplacemode.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.netty.buffer.Unpooled;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fireplacemode.FireplaceMain;
import net.minecraft.network.PacketByteBuf;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Inject(method = "sendWorldInfo", at = @At(value = "HEAD"))
    private void sendWorldInfoMixin(ServerPlayerEntity player, ServerWorld world, CallbackInfo info) {
        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
        data.writeBoolean(world.getGameRules().getBoolean(FireplaceMain.FIREPLACE_MODE));
        ServerPlayNetworking.send(player, FireplaceMain.FIREPLACE_MODE_PACKET, data);
    }
}
