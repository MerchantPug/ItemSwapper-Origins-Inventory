package net.merchantpug.itemswapperorigins;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.merchantpug.itemswapperorigins.networking.c2s.SwapPacket;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemSwapperOrigins implements ModInitializer {
    public static final String MODID = "itemswapperorigins";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Override
    public void onInitialize() {
        ServerPlayNetworking.registerGlobalReceiver(SwapPacket.ID, SwapPacket::handle);
    }

    public static Identifier identifier(String path) {
        return new Identifier(MODID, path);
    }
}
