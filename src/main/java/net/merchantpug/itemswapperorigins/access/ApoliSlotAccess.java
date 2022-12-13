package net.merchantpug.itemswapperorigins.access;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public interface ApoliSlotAccess {
    @Nullable Identifier getPowerId();
    void setPowerId(Identifier id);
}
