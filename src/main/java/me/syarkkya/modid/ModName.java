package me.syarkkya.modid;

import me.syarkkya.modid.config.ConfigLoader;
import net.fabricmc.api.ModInitializer;

public class ModName implements ModInitializer {
	@Override
	public void onInitialize() {
		ConfigLoader.init();
	}
}