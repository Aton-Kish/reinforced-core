package atonkish.reinfcore.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import atonkish.reinfcore.item.ItemGroupInterface;

@Mixin(ItemGroup.class)
public class ItemGroupMixin implements ItemGroupInterface {
    @Shadow
    private ItemStack icon;

    public void setIcon(Item item) {
        this.icon = new ItemStack(item);
    };

    public void setIcon(Block block) {
        this.icon = new ItemStack(block);
    };
}