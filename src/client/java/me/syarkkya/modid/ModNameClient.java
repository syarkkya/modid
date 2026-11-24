package me.syarkkya.modid;

import me.syarkkya.modid.config.ConfigLoaderClient;
import net.fabricmc.api.ClientModInitializer;

public class ModNameClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigLoaderClient.init();
    }
}