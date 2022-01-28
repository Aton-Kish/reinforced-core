package atonkish.reinfcore;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

import atonkish.reinfcore.api.ReinforcedCoreClientModInitializer;

@Environment(EnvType.CLIENT)
public class ReinforcedCoreClientMod implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// entrypoint: "reinfcoreclient"
		FabricLoader.getInstance()
				.getEntrypoints(ReinforcedCoreMod.MOD_ID + "client", ReinforcedCoreClientModInitializer.class)
				.forEach(ReinforcedCoreClientModInitializer::onInitializeReinforcedCoreClient);
	}
}
