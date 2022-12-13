package net.merchantpug.itemswapperorigins.mixin;

import dev.tr7zw.itemswapper.util.ItemUtil;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.InventoryPower;
import net.merchantpug.itemswapperorigins.access.ApoliSlotAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(value = ItemUtil.class, remap = false)
public abstract class ItemUtilMixin {
	@Shadow
	private static MinecraftClient minecraft;

	@Shadow
	private static void addUnstackableItems(List<ItemUtil.Slot> ids, ItemUtil.Slot slot) {}

	@Inject(method = "findSlotsMatchingItem", at = @At(value = "RETURN", ordinal = 2), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	private static void checkApoliInventoryPowerSlots(Item item, boolean limit, boolean ignoreHotbar, CallbackInfoReturnable<List<ItemUtil.Slot>> cir, DefaultedList<ItemStack> items, List<ItemUtil.Slot> ids) {
		boolean hasReturned = false;
		for (int i = 0; i < PowerHolderComponent.getPowers(minecraft.player, InventoryPower.class).size(); ++i) {
			InventoryPower power = PowerHolderComponent.getPowers(minecraft.player, InventoryPower.class).get(i);
			if (hasReturned) {
				cir.setReturnValue(ids);
				break;
			} else {
				for (int j = 0; j < power.size(); ++j) {
					ItemStack stack = power.getStack(j);
					if (!stack.isEmpty() && stack.getItem() == item) {
						ItemUtil.Slot slot = new ItemUtil.Slot(-1, j, stack);
						((ApoliSlotAccess)(Object)slot).setPowerId(power.getType().getIdentifier());
						addUnstackableItems(ids, slot);
						if (limit) {
							hasReturned = true;
							break;
						}
					}
				}
			}
		}
	}
}
