package atonkish.reinfcore.mixin;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemGroup.class)
public interface ItemGroupAccessor {
    @Mutable
    @Accessor("icon")
    public void setIcon(ItemStack itemStack);
}
