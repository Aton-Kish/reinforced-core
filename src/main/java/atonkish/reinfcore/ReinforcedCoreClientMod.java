package atonkish.reinfcore;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.screen.ScreenHandlerType;

import atonkish.reinfcore.client.gui.screen.ingame.ReinforcedStorageScreen;
import atonkish.reinfcore.screen.ModScreenHandlerType;
import atonkish.reinfcore.screen.ReinforcedStorageScreenHandler;

@Environment(EnvType.CLIENT)
public class ReinforcedCoreClientMod implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		registerScreen();
	}

	private static void registerScreen() {
		// Single Block Screen
		for (ScreenHandlerType<ReinforcedStorageScreenHandler> screenHandlerType : ModScreenHandlerType.REINFORCED_SINGLE_BLOCK_MAP
				.values()) {
			ScreenRegistry.register(screenHandlerType, ReinforcedStorageScreen::new);
		}

		// Double Block Screen
		for (ScreenHandlerType<ReinforcedStorageScreenHandler> screenHandlerType : ModScreenHandlerType.REINFORCED_DOUBLE_BLOCK_MAP
				.values()) {
			ScreenRegistry.register(screenHandlerType, ReinforcedStorageScreen::new);
		}

		// Shulker Box Screen
		for (ScreenHandlerType<ReinforcedStorageScreenHandler> screenHandlerType : ModScreenHandlerType.REINFORCED_SHULKER_BOX_MAP
				.values()) {
			ScreenRegistry.register(screenHandlerType, ReinforcedStorageScreen::new);
		}
	}

}