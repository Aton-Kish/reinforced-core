package atonkish.reinfcore.util;

import java.util.LinkedHashMap;

import net.minecraft.item.Item;

public class ReinforcingMaterials {
    public static final LinkedHashMap<String, ReinforcingMaterial> MAP = new LinkedHashMap<>();

    public static ReinforcingMaterial register(String name, int size, Item ingredient) {
        if (!MAP.containsKey(name)) {
            MAP.put(name, new ReinforcingMaterial(name, size, ingredient));
        }

        return MAP.get(name);
    }
}
