package atonkish.reinfcore;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import atonkish.reinfcore.item.ModItemGroup;
import atonkish.reinfcore.screen.ModScreenHandlerType;

public class ReinforcedCoreMod implements ModInitializer {
	public static final String MOD_ID = "reinfcore";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Items
		ModItemGroup.init();

		// Screens
		ModScreenHandlerType.init();
	}
}
