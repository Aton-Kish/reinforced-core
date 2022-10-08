package atonkish.reinfcore.screen;

import java.util.LinkedHashMap;
import java.util.Map;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry.SimpleClientHandlerFactory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import atonkish.reinfcore.ReinforcedCoreMod;
import atonkish.reinfcore.util.ReinforcingMaterial;

public class ModScreenHandlerType {
    public static final Map<ReinforcingMaterial, ScreenHandlerType<ReinforcedStorageScreenHandler>> REINFORCED_SINGLE_BLOCK_MAP = new LinkedHashMap<>();
    public static final Map<ReinforcingMaterial, ScreenHandlerType<ReinforcedStorageScreenHandler>> REINFORCED_DOUBLE_BLOCK_MAP = new LinkedHashMap<>();
    public static final Map<ReinforcingMaterial, ScreenHandlerType<ReinforcedStorageScreenHandler>> REINFORCED_SHULKER_BOX_MAP = new LinkedHashMap<>();

    public static ScreenHandlerType<ReinforcedStorageScreenHandler> registerMaterialSingleBlock(
            ReinforcingMaterial material) {
        if (!REINFORCED_SINGLE_BLOCK_MAP.containsKey(material)) {
            String id = "single_" + material.getName() + "_block";
            SimpleClientHandlerFactory<ReinforcedStorageScreenHandler> factory = createSingleBlockScreenHandlerFactory(
                    material);
            ScreenHandlerType<ReinforcedStorageScreenHandler> screenHandlerType = register(id, factory);
            REINFORCED_SINGLE_BLOCK_MAP.put(material, screenHandlerType);
        }

        return REINFORCED_SINGLE_BLOCK_MAP.get(material);
    }

    public static ScreenHandlerType<ReinforcedStorageScreenHandler> registerMaterialDoubleBlock(
            ReinforcingMaterial material) {
        if (!REINFORCED_DOUBLE_BLOCK_MAP.containsKey(material)) {
            String id = "double_" + material.getName() + "_block";
            SimpleClientHandlerFactory<ReinforcedStorageScreenHandler> factory = createDoubleBlockScreenHandlerFactory(
                    material);
            ScreenHandlerType<ReinforcedStorageScreenHandler> screenHandlerType = register(id, factory);
            REINFORCED_DOUBLE_BLOCK_MAP.put(material, screenHandlerType);
        }

        return REINFORCED_DOUBLE_BLOCK_MAP.get(material);
    }

    public static ScreenHandlerType<ReinforcedStorageScreenHandler> registerMaterialShulkerBox(
            ReinforcingMaterial material) {
        if (!REINFORCED_SHULKER_BOX_MAP.containsKey(material)) {
            String id = material.getName() + "_shulker_box";
            SimpleClientHandlerFactory<ReinforcedStorageScreenHandler> factory = createShulkerBoxScreenHandlerFactory(
                    material);
            ScreenHandlerType<ReinforcedStorageScreenHandler> screenHandlerType = register(id, factory);
            REINFORCED_SHULKER_BOX_MAP.put(material, screenHandlerType);
        }

        return REINFORCED_SHULKER_BOX_MAP.get(material);
    }

    private static ScreenHandlerType<ReinforcedStorageScreenHandler> register(String id,
            SimpleClientHandlerFactory<ReinforcedStorageScreenHandler> factory) {
        Identifier identifier = new Identifier(ReinforcedCoreMod.MOD_ID, id);
        return ScreenHandlerRegistry.registerSimple(identifier, factory);
    }

    private static SimpleClientHandlerFactory<ReinforcedStorageScreenHandler> createSingleBlockScreenHandlerFactory(
            ReinforcingMaterial material) {
        return (int syncId, PlayerInventory playerInventory) -> ReinforcedStorageScreenHandler
                .createShulkerBoxScreen(material, syncId, playerInventory);
    }

    private static SimpleClientHandlerFactory<ReinforcedStorageScreenHandler> createDoubleBlockScreenHandlerFactory(
            ReinforcingMaterial material) {
        return (int syncId, PlayerInventory playerInventory) -> ReinforcedStorageScreenHandler
                .createDoubleBlockScreen(material, syncId, playerInventory);
    }

    private static SimpleClientHandlerFactory<ReinforcedStorageScreenHandler> createShulkerBoxScreenHandlerFactory(
            ReinforcingMaterial material) {
        return (int syncId, PlayerInventory playerInventory) -> ReinforcedStorageScreenHandler
                .createShulkerBoxScreen(material, syncId, playerInventory);
    }
}
