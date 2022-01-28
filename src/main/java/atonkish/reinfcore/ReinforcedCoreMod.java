package atonkish.reinfcore;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import atonkish.reinfcore.api.ReinforcedCoreModInitializer;
import atonkish.reinfcore.item.ModItemGroup;

public class ReinforcedCoreMod implements ModInitializer {
	public static final String MOD_ID = "reinfcore";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Items
		ModItemGroup.init();

		// entrypoint: "reinfcore"
		FabricLoader.getInstance()
				.getEntrypoints(MOD_ID, ReinforcedCoreModInitializer.class)
				.forEach(ReinforcedCoreModInitializer::onInitializeReinforcedCore);
	}
}
