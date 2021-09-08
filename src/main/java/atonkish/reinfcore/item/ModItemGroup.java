package atonkish.reinfcore.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

import atonkish.reinfcore.ReinforcedCoreMod;

public class ModItemGroup {
    public static final ItemGroup REINFORCED_STORAGE;

    public static void init() {
    }

    private static ItemGroup create(String id) {
        Identifier identifier = new Identifier(ReinforcedCoreMod.MOD_ID, id);
        return FabricItemGroupBuilder.create(identifier).build();
    }

    static {
        REINFORCED_STORAGE = create("reinforced_storage");
    }
}
