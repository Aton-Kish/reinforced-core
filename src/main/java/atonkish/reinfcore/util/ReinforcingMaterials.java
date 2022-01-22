package atonkish.reinfcore.util;

import java.util.LinkedHashMap;

public class ReinforcingMaterials {
    public static final LinkedHashMap<String, ReinforcingMaterial> MAP = new LinkedHashMap<>();

    public static ReinforcingMaterial register(ReinforcingMaterial material) {
        if (!MAP.containsKey(material.getName())) {
            MAP.put(material.getName(), material);
        }

        return material;
    }
}
