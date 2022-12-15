package net.fireplacemode;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fireplacemode.access.HudAccess;

public class FireplaceClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(FireplaceMain.SAVING_PACKET, (client, handler, buf, sender) -> {
            ((HudAccess) client.inGameHud).startSaving();
        });

    }

}