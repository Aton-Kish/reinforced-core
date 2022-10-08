package atonkish.reinfcore.api;

import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandlerType;

import atonkish.reinfcore.screen.ModScreenHandlerType;
import atonkish.reinfcore.screen.ReinforcedStorageScreenHandler;
import atonkish.reinfcore.util.ReinforcedStorageScreenModel;
import atonkish.reinfcore.util.ReinforcedStorageScreenModels;
import atonkish.reinfcore.util.ReinforcingMaterial;
import atonkish.reinfcore.util.ReinforcingMaterials;

public class ReinforcedCoreRegistry {
    public static ReinforcingMaterial registerReinforcingMaterial(String name, int size, Item ingredient) {
        return ReinforcingMaterials.register(name, size, ingredient);
    }

    public static ReinforcedStorageScreenModel registerMaterialSingleBlockScreenModel(
            ReinforcingMaterial material) {
        return ReinforcedStorageScreenModels.registerMaterialSingleBlock(material);
    }

    public static ReinforcedStorageScreenModel registerMaterialDoubleBlockScreenModel(
            ReinforcingMaterial material) {
        return ReinforcedStorageScreenModels.registerMaterialDoubleBlock(material);
    }

    public static ScreenHandlerType<ReinforcedStorageScreenHandler> registerMaterialSingleBlockScreenHandler(
            ReinforcingMaterial material) {
        return ModScreenHandlerType.registerMaterialSingleBlock(material);
    }

    public static ScreenHandlerType<ReinforcedStorageScreenHandler> registerMaterialDoubleBlockScreenHandler(
            ReinforcingMaterial material) {
        return ModScreenHandlerType.registerMaterialDoubleBlock(material);
    }

    public static ScreenHandlerType<ReinforcedStorageScreenHandler> registerMaterialShulkerBoxScreenHandler(
            ReinforcingMaterial material) {
        return ModScreenHandlerType.registerMaterialShulkerBox(material);
    }
}
