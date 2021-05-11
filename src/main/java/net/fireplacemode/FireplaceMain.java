package net.fireplacemode;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;

public class FireplaceMain implements ClientModInitializer {

    public static final Tag<Block> SAVE_BLOCK = TagRegistry.block(new Identifier("fireplacemode", "save_block"));
    public static final Identifier FIREPLACE_MODE_PACKET = new Identifier("dragonloot", "dragon_anvil_sync");

    public static final GameRules.Key<GameRules.BooleanRule> FIREPLACE_MODE = GameRuleRegistry.register("fireplaceMode",
            GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(FIREPLACE_MODE_PACKET,
                (client, handler, buffer, responseSender) -> {
                    boolean fireplace_mode = buffer.readBoolean();
                    client.execute(() -> {
                        ((GameRules.BooleanRule) client.world.getLevelProperties().getGameRules().get(FIREPLACE_MODE))
                                .set(fireplace_mode, (MinecraftServer) null);
                    });
                });
    }

}