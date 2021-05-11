package net.fireplacemode.mixin;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fireplacemode.FireplaceMain;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(CommandManager.class)
public class CommandManagerMixin {

    @Inject(method = "execute", at = @At(value = "HEAD"))
    public void executeMixin(ServerCommandSource commandSource, String command, CallbackInfoReturnable<Integer> info) {
        if (command.contains("gamerule fireplaceMode") && (command.endsWith("true") || command.endsWith("false"))) {
            PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
            if (command.endsWith("true")) {
                data.writeBoolean(true);
            } else if (command.endsWith("false")) {
                data.writeBoolean(false);
            }

            Iterator<ServerPlayerEntity> var2 = commandSource.getWorld().getServer().getPlayerManager().getPlayerList()
                    .iterator();
            while (var2.hasNext()) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) var2.next();
                ServerPlayNetworking.send(serverPlayerEntity, FireplaceMain.FIREPLACE_MODE_PACKET, data);
            }

        }
    }

}