package atonkish.reinfcore.util;

import java.util.LinkedHashMap;

public class ReinforcedStorageScreenModels {
    public static final LinkedHashMap<ReinforcingMaterial, ReinforcedStorageScreenModel> SINGLE_MAP = new LinkedHashMap<>();
    public static final LinkedHashMap<ReinforcingMaterial, ReinforcedStorageScreenModel> DOUBLE_MAP = new LinkedHashMap<>();

    public static ReinforcedStorageScreenModel registerMaterialSingleBlock(ReinforcingMaterial material) {
        ReinforcedStorageScreenModel screenModel = new ReinforcedStorageScreenModel(material, false);

        SINGLE_MAP.put(material, screenModel);

        return screenModel;
    }

    public static ReinforcedStorageScreenModel registerMaterialDoubleBlock(ReinforcingMaterial material) {
        ReinforcedStorageScreenModel screenModel = new ReinforcedStorageScreenModel(material, true);

        DOUBLE_MAP.put(material, screenModel);

        return screenModel;
    }
}
