package atonkish.reinfcore;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import atonkish.reinfcore.api.ReinforcedCoreModInitializer;
import atonkish.reinfcore.item.ModItemGroup;

public class ReinforcedCoreMod implements ModInitializer {
	public static final String MOD_ID = "reinfcore";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ReinforcedCoreConfig CONFIG;

	@Override
	public void onInitialize() {
		// Auto Config
		AutoConfig.register(ReinforcedCoreConfig.class, GsonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(ReinforcedCoreConfig.class).getConfig();

		// Items
		ModItemGroup.init();

		// entrypoint: "reinfcore"
		FabricLoader.getInstance()
				.getEntrypoints(MOD_ID, ReinforcedCoreModInitializer.class)
				.forEach(ReinforcedCoreModInitializer::onInitializeReinforcedCore);
	}
}
