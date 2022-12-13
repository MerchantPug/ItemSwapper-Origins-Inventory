package net.merchantpug.itemswapperorigins.mixin;

import dev.tr7zw.itemswapper.overlay.ItemListOverlay;
import dev.tr7zw.itemswapper.util.ItemUtil;
import net.merchantpug.itemswapperorigins.access.ApoliSlotAccess;
import net.merchantpug.itemswapperorigins.networking.c2s.SwapPacket;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = ItemListOverlay.class, remap = false)
public class ItemListOverlayMixin {
    @Inject(method = "onClose", at = @At(value = "INVOKE", target = "Ldev/tr7zw/itemswapper/util/ItemUtil$Slot;inventory()I", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void updateApoliInventory(CallbackInfo ci, ItemUtil.Slot slot) {
        @Nullable Identifier powerId = ((ApoliSlotAccess)(Object)slot).getPowerId();
        if (powerId != null) {
            SwapPacket.send(powerId, slot.slot());
            ci.cancel();
        }
    }
}
