package atonkish.reinfcore.screen;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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
            ScreenHandlerType.Factory<ReinforcedStorageScreenHandler> factory = createSingleBlockScreenHandlerFactory(
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
            ScreenHandlerType.Factory<ReinforcedStorageScreenHandler> factory = createDoubleBlockScreenHandlerFactory(
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
            ScreenHandlerType.Factory<ReinforcedStorageScreenHandler> factory = createShulkerBoxScreenHandlerFactory(
                    material);
            ScreenHandlerType<ReinforcedStorageScreenHandler> screenHandlerType = register(id, factory);
            REINFORCED_SHULKER_BOX_MAP.put(material, screenHandlerType);
        }

        return REINFORCED_SHULKER_BOX_MAP.get(material);
    }

    private static ScreenHandlerType<ReinforcedStorageScreenHandler> register(String id,
            ScreenHandlerType.Factory<ReinforcedStorageScreenHandler> factory) {
        Identifier identifier = new Identifier(ReinforcedCoreMod.MOD_ID, id);
        ScreenHandlerType<ReinforcedStorageScreenHandler> type = new ScreenHandlerType<ReinforcedStorageScreenHandler>(
                factory);
        return Registry.register(Registry.SCREEN_HANDLER, identifier, type);
    }

    private static ScreenHandlerType.Factory<ReinforcedStorageScreenHandler> createSingleBlockScreenHandlerFactory(
            ReinforcingMaterial material) {
        return (int syncId, PlayerInventory playerInventory) -> ReinforcedStorageScreenHandler
                .createShulkerBoxScreen(material, syncId, playerInventory);
    }

    private static ScreenHandlerType.Factory<ReinforcedStorageScreenHandler> createDoubleBlockScreenHandlerFactory(
            ReinforcingMaterial material) {
        return (int syncId, PlayerInventory playerInventory) -> ReinforcedStorageScreenHandler
                .createDoubleBlockScreen(material, syncId, playerInventory);
    }

    private static ScreenHandlerType.Factory<ReinforcedStorageScreenHandler> createShulkerBoxScreenHandlerFactory(
            ReinforcingMaterial material) {
        return (int syncId, PlayerInventory playerInventory) -> ReinforcedStorageScreenHandler
                .createShulkerBoxScreen(material, syncId, playerInventory);
    }
}
