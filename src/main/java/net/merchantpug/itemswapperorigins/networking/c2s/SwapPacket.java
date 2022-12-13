package net.merchantpug.itemswapperorigins.networking.c2s;

import io.github.apace100.apoli.power.InventoryPower;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.merchantpug.itemswapperorigins.ItemSwapperOrigins;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class SwapPacket {
    public static final Identifier ID = ItemSwapperOrigins.identifier("swap");

    public static void send(Identifier powerId, int slot) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeIdentifier(powerId);
        buf.writeInt(slot);
        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        Identifier powerId = buf.readIdentifier();
        int slot = buf.readInt();

        server.execute(() -> {
            InventoryPower power = new PowerTypeReference<InventoryPower>(powerId).get(player);
            int selectedSlot = player.getInventory().selectedSlot;
            ItemStack stack = player.getInventory().getStack(selectedSlot).copy();
            player.getInventory().setStack(selectedSlot, power.getContainer().get(slot));
            player.getInventory().markDirty();
            power.setStack(slot, stack);
        });
    }
}
