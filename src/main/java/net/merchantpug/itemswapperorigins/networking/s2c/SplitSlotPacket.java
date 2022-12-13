package net.merchantpug.itemswapperorigins.networking.s2c;

import io.github.apace100.apoli.power.InventoryPower;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.merchantpug.itemswapperorigins.ItemSwapperOrigins;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class SplitSlotPacket {
    public static final Identifier ID = ItemSwapperOrigins.identifier("split_slot");

    public static void send(ServerPlayerEntity player, Identifier powerId, int slot, int amount) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(player.getId());
        buf.writeIdentifier(powerId);
        buf.writeInt(slot);
        buf.writeInt(amount);
        ServerPlayNetworking.send(player, ID, buf);
    }


    public static void handle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        int entityId = buf.readInt();
        Identifier powerId = buf.readIdentifier();
        int slot = buf.readInt();
        int amount = buf.readInt();

        client.execute(() -> {
            Entity entity = client.world.getEntityById(entityId);
            if (!(entity instanceof PlayerEntity player)) {
                ItemSwapperOrigins.LOGGER.warn("Tried swapping inventory power slot on non player entity.");
                return;
            }
            InventoryPower power = new PowerTypeReference<InventoryPower>(powerId).get(player);
            power.removeStack(slot, amount);
        });
    }
}
