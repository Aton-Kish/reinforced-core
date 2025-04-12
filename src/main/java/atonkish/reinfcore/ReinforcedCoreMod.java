package atonkish.reinfcore;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryLoader;
import net.minecraft.test.TestEnvironmentDefinition;
import net.minecraft.test.TestInstance;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.impl.gametest.FabricGameTestRunner;
import net.fabricmc.loader.api.FabricLoader;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import atonkish.reinfcore.api.ReinforcedCoreModInitializer;
import atonkish.reinfcore.gametest.TestAnnotationLocator;
import atonkish.reinfcore.gametest.TestFunction;
import atonkish.reinfcore.item.ModItemGroups;

public class ReinforcedCoreMod implements ModInitializer {
	public static final String MOD_ID = "reinfcore";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ReinforcedCoreConfig CONFIG;
	private static TestAnnotationLocator locator = new TestAnnotationLocator(FabricLoader.getInstance());

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
		this.onInitializeReinforcedCoreGameTest();
	}

	private void onInitializeReinforcedCoreGameTest() {
		if (!(FabricGameTestRunner.ENABLED || FabricLoader.getInstance().isDevelopmentEnvironment())) {
			return;
		}

		for (TestFunction testFunction : locator.getTestFunctions()) {
			LOGGER.debug("Registering test function: {}", testFunction.identifier());
			Registry.register(Registries.TEST_FUNCTION, testFunction.identifier(), testFunction.testFunction());
		}
	}

	public static void registerDynamicEntries(List<RegistryLoader.Loader<?>> registriesList) {
		Map<RegistryKey<? extends Registry<?>>, Registry<?>> registries = new IdentityHashMap<>(registriesList.size());

		for (RegistryLoader.Loader<?> entry : registriesList) {
			registries.put(entry.registry().getKey(), entry.registry());
		}

		Registry<TestInstance> testInstances = (Registry<TestInstance>) registries.get(RegistryKeys.TEST_INSTANCE);
		Registry<TestEnvironmentDefinition> testEnvironmentDefinitionRegistry = (Registry<TestEnvironmentDefinition>) Objects
				.requireNonNull(registries.get(RegistryKeys.TEST_ENVIRONMENT));

		for (TestFunction testFunction : locator.getTestFunctions()) {
			TestInstance testInstance = testFunction.testInstance(testEnvironmentDefinitionRegistry);
			Registry.register(testInstances, testFunction.identifier(), testInstance);
		}
	}
}
