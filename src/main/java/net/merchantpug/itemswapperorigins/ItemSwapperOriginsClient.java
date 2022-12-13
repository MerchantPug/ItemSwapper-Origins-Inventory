package net.merchantpug.itemswapperorigins;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.merchantpug.itemswapperorigins.networking.s2c.SplitSlotPacket;
import net.merchantpug.itemswapperorigins.networking.s2c.UpdateSlotPacket;

public class ItemSwapperOriginsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.INIT.register((clientPlayNetworkHandler, minecraftClient) -> {
            ClientPlayNetworking.registerReceiver(UpdateSlotPacket.ID, UpdateSlotPacket::handle);
            ClientPlayNetworking.registerReceiver(SplitSlotPacket.ID, SplitSlotPacket::handle);
        });
    }
}
