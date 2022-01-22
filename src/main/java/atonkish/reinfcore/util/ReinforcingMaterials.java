package atonkish.reinfcore.util;

import java.util.LinkedHashMap;

import net.minecraft.item.Item;

public class ReinforcingMaterials {
    public static final LinkedHashMap<String, ReinforcingMaterial> MAP = new LinkedHashMap<>();

    public static void register(String name, int size, Item ingredient) {
        ReinforcingMaterial material = new ReinforcingMaterial(name, size, ingredient);
        if (!MAP.containsKey(name)) {
            MAP.put(name, material);
        }
    }
}
