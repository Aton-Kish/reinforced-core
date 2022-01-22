package atonkish.reinfcore.util;

import java.util.LinkedHashMap;

public class ReinforcedStorageScreenModels {
    public static final LinkedHashMap<ReinforcingMaterial, ReinforcedStorageScreenModel> SINGLE_MAP = new LinkedHashMap<>();
    public static final LinkedHashMap<ReinforcingMaterial, ReinforcedStorageScreenModel> DOUBLE_MAP = new LinkedHashMap<>();

    public static ReinforcedStorageScreenModel registerSingleBlockScreenModel(ReinforcingMaterial material) {
        ReinforcedStorageScreenModel screenModel = new ReinforcedStorageScreenModel(material, false);
        if (!SINGLE_MAP.containsKey(material)) {
            SINGLE_MAP.put(material, screenModel);
        }

        return screenModel;
    }

    public static ReinforcedStorageScreenModel registerDoubleBlockScreenModel(ReinforcingMaterial material) {
        ReinforcedStorageScreenModel screenModel = new ReinforcedStorageScreenModel(material, true);
        if (!DOUBLE_MAP.containsKey(material)) {
            DOUBLE_MAP.put(material, screenModel);
        }

        return screenModel;
    }
}
