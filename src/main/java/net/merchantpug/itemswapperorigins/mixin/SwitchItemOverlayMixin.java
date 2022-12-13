package net.merchantpug.itemswapperorigins.mixin;

import dev.tr7zw.itemswapper.overlay.SwitchItemOverlay;
import dev.tr7zw.itemswapper.util.ItemUtil;
import net.merchantpug.itemswapperorigins.access.ApoliSlotAccess;
import net.merchantpug.itemswapperorigins.networking.c2s.SwapPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(value = SwitchItemOverlay.class, remap = false)
public class SwitchItemOverlayMixin {
    @Shadow @Final public MinecraftClient minecraft;

    @Inject(method = "onClose", at = @At(value = "INVOKE", target = "Ldev/tr7zw/itemswapper/util/ItemUtil$Slot;inventory()I", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void updateApoliInventory(CallbackInfo ci, List<ItemUtil.Slot> slots, ItemUtil.Slot slot) {
        @Nullable Identifier powerId = ((ApoliSlotAccess)(Object)slot).getPowerId();
        if (powerId != null) {
            SwapPacket.send(powerId, slot.slot());
            ci.cancel();
        }
    }
}
