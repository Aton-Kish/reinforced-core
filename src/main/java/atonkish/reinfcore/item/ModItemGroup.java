package atonkish.reinfcore.item;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import atonkish.reinfcore.mixin.ItemGroupAccessor;

public class ModItemGroup {
    public static void setIcon(ItemGroup itemGroup, ItemConvertible item) {
        ItemStack itemStack = new ItemStack(item);
        ((ItemGroupAccessor) itemGroup).setIcon(itemStack);
    }
}
