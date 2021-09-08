package atonkish.reinfcore.screen;

import java.util.HashMap;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry.SimpleClientHandlerFactory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import atonkish.reinfcore.ReinforcedCoreMod;
import atonkish.reinfcore.util.ReinforcingMaterial;

public class ModScreenHandlerType {
    public static final HashMap<ReinforcingMaterial, ScreenHandlerType<ReinforcedStorageScreenHandler>> REINFORCED_SINGLE_BLOCK_MAP;
    public static final HashMap<ReinforcingMaterial, ScreenHandlerType<ReinforcedStorageScreenHandler>> REINFORCED_DOUBLE_BLOCK_MAP;
    public static final HashMap<ReinforcingMaterial, ScreenHandlerType<ReinforcedStorageScreenHandler>> REINFORCED_SHULKER_BOX_MAP;

    public static void init() {
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

    static {
        REINFORCED_SINGLE_BLOCK_MAP = new HashMap<>();
        REINFORCED_DOUBLE_BLOCK_MAP = new HashMap<>();
        REINFORCED_SHULKER_BOX_MAP = new HashMap<>();
        for (ReinforcingMaterial material : ReinforcingMaterial.values()) {
            // Single Block
            ScreenHandlerType<ReinforcedStorageScreenHandler> singleBlockScreenHandlerType = register(
                    "single_" + material.getName() + "_block", createSingleBlockScreenHandlerFactory(material));
            REINFORCED_SINGLE_BLOCK_MAP.put(material, singleBlockScreenHandlerType);

            // Double Block
            ScreenHandlerType<ReinforcedStorageScreenHandler> doubleBlockScreenHandlerType = register(
                    "double_" + material.getName() + "_block", createDoubleBlockScreenHandlerFactory(material));
            REINFORCED_DOUBLE_BLOCK_MAP.put(material, doubleBlockScreenHandlerType);

            // Shulker Box
            ScreenHandlerType<ReinforcedStorageScreenHandler> shulkerBoxScreenHandlerType = register(
                    material.getName() + "_shulker_box", createShulkerBoxScreenHandlerFactory(material));
            REINFORCED_SHULKER_BOX_MAP.put(material, shulkerBoxScreenHandlerType);
        }
    }
}
