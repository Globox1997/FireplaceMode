package net.fireplacemode.mixin;

import java.util.Iterator;
import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.netty.buffer.Unpooled;

import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fireplacemode.FireplaceClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Shadow
    private PlayerManager playerManager;
    @Shadow
    @Final
    private Map<RegistryKey<World>, ServerWorld> worlds;

    @Inject(method = "save", at = @At(value = "HEAD"))
    private void saveMixin(boolean suppressLogs, boolean bl, boolean bl2, CallbackInfoReturnable<Boolean> info) {
        if (FireplaceClient.CONFIG.hud_save) {
            Iterator<ServerPlayerEntity> var2 = playerManager.getPlayerList().iterator();
            while (var2.hasNext()) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) var2.next();
                ServerPlayNetworking.send(serverPlayerEntity, FireplaceClient.SAVING_PACKET,
                        new PacketByteBuf(Unpooled.buffer()));
            }
        }
    }

}