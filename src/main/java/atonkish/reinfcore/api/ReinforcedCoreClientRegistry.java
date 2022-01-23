package atonkish.reinfcore.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

import atonkish.reinfcore.client.gui.screen.ingame.ReinforcedStorageScreen;
import atonkish.reinfcore.screen.ModScreenHandlerType;
import atonkish.reinfcore.util.ReinforcingMaterial;

@Environment(EnvType.CLIENT)
public class ReinforcedCoreClientRegistry {
    public static void registerMaterialSingleBlockScreen(ReinforcingMaterial material) {
        ScreenRegistry.register(ModScreenHandlerType.REINFORCED_SINGLE_BLOCK_MAP.get(material),
                ReinforcedStorageScreen::new);
    }

    public static void registerMaterialDoubleBlockScreen(ReinforcingMaterial material) {
        ScreenRegistry.register(ModScreenHandlerType.REINFORCED_DOUBLE_BLOCK_MAP.get(material),
                ReinforcedStorageScreen::new);
    }

    public static void registerMaterialShulkerBoxScreen(ReinforcingMaterial material) {
        ScreenRegistry.register(ModScreenHandlerType.REINFORCED_SHULKER_BOX_MAP.get(material),
                ReinforcedStorageScreen::new);
    }
}
