package net.merchantpug.itemswapperorigins.mixin;

import dev.tr7zw.itemswapper.util.ItemUtil;
import net.merchantpug.itemswapperorigins.access.ApoliSlotAccess;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ItemUtil.Slot.class, remap = false)
@Implements(@Interface(iface = ApoliSlotAccess.class, prefix = "itemswapperorigins$"))
public class ItemUtilSlotMixin {
    private Identifier itemswapperorigins$powerId;

    @Nullable
    public Identifier itemswapperorigins$getPowerId() {
        return itemswapperorigins$powerId;
    }

    public void itemswapperorigins$setPowerId(Identifier id) {
        itemswapperorigins$powerId = id;
    }
}
