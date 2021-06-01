package net.fireplacemode;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fireplacemode.access.HudAccess;
import net.fireplacemode.config.FireplaceConfig;
import net.minecraft.util.Identifier;

public class FireplaceClient implements ClientModInitializer {

    public static final Identifier FIREPLACE_MODE_PACKET = new Identifier("fireplacemode", "fireplace_mode");
    public static final Identifier SAVING_PACKET = new Identifier("fireplacemode", "saving");
    public static FireplaceConfig CONFIG = new FireplaceConfig();

    @Override
    public void onInitializeClient() {

        AutoConfig.register(FireplaceConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(FireplaceConfig.class).getConfig();
        ClientPlayNetworking.registerGlobalReceiver(SAVING_PACKET, (client, handler, buf, sender) -> {
            ((HudAccess) client.inGameHud).startSaving();
        });

    }

}