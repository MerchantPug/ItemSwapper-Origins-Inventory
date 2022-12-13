package net.merchantpug.itemswapperorigins.mixin;

import io.github.apace100.apoli.power.InventoryPower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.merchantpug.itemswapperorigins.networking.s2c.SplitSlotPacket;
import net.merchantpug.itemswapperorigins.networking.s2c.UpdateSlotPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryPower.class)
public class InventoryPowerMixin extends Power {
    public InventoryPowerMixin(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    @Inject(method = "setStack", at = @At("TAIL"))
    private void syncWhenSettingStack(int slot, ItemStack stack, CallbackInfo ci) {
        if (entity.world.isClient) return;
        UpdateSlotPacket.send((ServerPlayerEntity) entity, this.getType().getIdentifier(), slot, stack);
    }

    @Inject(method = "removeStack(II)Lnet/minecraft/item/ItemStack;", at = @At("TAIL"))
    private void syncWhenSplittingStack(int slot, int amount, CallbackInfoReturnable<ItemStack> cir) {
        if (entity.world.isClient) return;
        SplitSlotPacket.send((ServerPlayerEntity) entity, this.getType().getIdentifier(), slot, amount);
    }
}
