package atonkish.reinfcore.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

import atonkish.reinfcore.ReinforcedCoreMod;

public class ModItemGroups {
    public static final ItemGroup REINFORCED_STORAGE;

    public static void init() {
    }

    static {
        REINFORCED_STORAGE = FabricItemGroup
                .builder(new Identifier(ReinforcedCoreMod.MOD_ID, "reinforced_storage"))
                .build();
    }
}
