package atonkish.reinfcore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

import net.minecraft.test.TestFunctions;

import atonkish.reinfcore.api.ReinforcedCoreModInitializer;
import atonkish.reinfcore.item.ModItemGroups;

public class ReinforcedCoreMod implements ModInitializer {
	public static final String MOD_ID = "reinfcore";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ReinforcedCoreConfig CONFIG;
	private static final Map<Class<?>, String> GAME_TEST_IDS = new HashMap<>();

	@Override
	public void onInitialize() {
		// Auto Config
		AutoConfig.register(ReinforcedCoreConfig.class, GsonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(ReinforcedCoreConfig.class).getConfig();

		// Items
		ModItemGroups.init();

		// entrypoint: "reinfcore"
		FabricLoader.getInstance()
				.getEntrypoints(MOD_ID, ReinforcedCoreModInitializer.class)
				.forEach(ReinforcedCoreModInitializer::onInitializeReinforcedCore);

		// entrypoint: "reinfcore-gametest"
		ReinforcedCoreMod.initializeReinforcedCoreGameTest();
	}

	private static void initializeReinforcedCoreGameTest() {
		List<EntrypointContainer<Object>> entrypointContainers = FabricLoader.getInstance()
				.getEntrypointContainers(String.format("%s-gametest", MOD_ID), Object.class);

		for (EntrypointContainer<Object> container : entrypointContainers) {
			Class<?> testClass = container.getEntrypoint().getClass();
			String modid = container.getProvider().getMetadata().getId();

			if (GAME_TEST_IDS.containsKey(testClass)) {
				throw new UnsupportedOperationException("Test class (%s) has already been registered with mod (%s)"
						.formatted(testClass.getCanonicalName(), modid));
			}

			GAME_TEST_IDS.put(testClass, modid);
			TestFunctions.register(testClass);

			LOGGER.debug("Registered test class {} for mod {}", testClass.getCanonicalName(), modid);
		}
	}
}
