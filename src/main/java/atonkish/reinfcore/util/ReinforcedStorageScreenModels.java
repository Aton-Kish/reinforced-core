package atonkish.reinfcore.util;

import java.util.LinkedHashMap;

public class ReinforcedStorageScreenModels {
    public static final LinkedHashMap<ReinforcingMaterial, ReinforcedStorageScreenModel> SINGLE_MAP = new LinkedHashMap<>();
    public static final LinkedHashMap<ReinforcingMaterial, ReinforcedStorageScreenModel> DOUBLE_MAP = new LinkedHashMap<>();

    public static void register(ReinforcingMaterial material) {
        if (!SINGLE_MAP.containsKey(material)) {
            SINGLE_MAP.put(material, new ReinforcedStorageScreenModel(material, false));
        }

        if (!DOUBLE_MAP.containsKey(material)) {
            DOUBLE_MAP.put(material, new ReinforcedStorageScreenModel(material, true));
        }
    }
}
