package atonkish.reinfcore.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import atonkish.reinfcore.ReinforcedCoreMod;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> REINFORCED_STORAGE;

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, ModItemGroups.REINFORCED_STORAGE, FabricItemGroup
                .builder()
                .displayName(Text.translatable(String.format("itemGroup.%s.%s", ReinforcedCoreMod.MOD_ID,
                        ModItemGroups.REINFORCED_STORAGE.getValue().getPath())))
                .build());
    }

    private static RegistryKey<ItemGroup> register(String id) {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(ReinforcedCoreMod.MOD_ID, id));
    }

    static {
        REINFORCED_STORAGE = ModItemGroups.register("reinforced_storage");
    }
}
