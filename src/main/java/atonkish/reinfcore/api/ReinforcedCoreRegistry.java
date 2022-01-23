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

    public static ReinforcedStorageScreenModel registerSingleBlockScreenModel(
            ReinforcingMaterial material) {
        return ReinforcedStorageScreenModels.registerSingleBlockScreenModel(material);
    }

    public static ReinforcedStorageScreenModel registerDoubleBlockScreenModel(
            ReinforcingMaterial material) {
        return ReinforcedStorageScreenModels.registerDoubleBlockScreenModel(material);
    }

    public static ScreenHandlerType<ReinforcedStorageScreenHandler> registerSingleBlockScreenHandler(
            ReinforcingMaterial material) {
        return ModScreenHandlerType.registerSingleBlockScreenHandler(material);
    }

    public static ScreenHandlerType<ReinforcedStorageScreenHandler> registerDoubleBlockScreenHandler(
            ReinforcingMaterial material) {
        return ModScreenHandlerType.registerDoubleBlockScreenHandler(material);
    }

    public static ScreenHandlerType<ReinforcedStorageScreenHandler> registerShulkerBoxScreenHandler(
            ReinforcingMaterial material) {
        return ModScreenHandlerType.registerShulkerBoxScreenHandler(material);
    }
}
