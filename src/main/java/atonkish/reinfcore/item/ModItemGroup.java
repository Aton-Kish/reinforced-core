package atonkish.reinfcore.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import atonkish.reinfcore.ReinforcedCoreMod;
import atonkish.reinfcore.mixin.ItemGroupAccessor;

public class ModItemGroup {
    public static final ItemGroup REINFORCED_STORAGE;

    public static void init() {
    }

    public static void setIcon(ItemGroup itemGroup, ItemConvertible item) {
        ItemStack itemStack = new ItemStack(item);
        ((ItemGroupAccessor) itemGroup).setIcon(itemStack);
    }

    private static ItemGroup create(String id) {
        Identifier identifier = new Identifier(ReinforcedCoreMod.MOD_ID, id);
        return FabricItemGroupBuilder.create(identifier).build();
    }

    static {
        REINFORCED_STORAGE = create("reinforced_storage");
    }
}
