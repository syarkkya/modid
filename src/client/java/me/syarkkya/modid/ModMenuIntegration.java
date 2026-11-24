package me.syarkkya.modid;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.syarkkya.modid.config.ConfigScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            return ConfigScreen::createScreen;
        }

        return (parent) -> new ConfirmLinkScreen((confirmed) -> {
            if (confirmed) {
                Util.getPlatform().openUri("https://modrinth.com/mod/yacl/");
            }
            Minecraft.getInstance().setScreen(parent);
        },
                Component.translatable("modid.config.yaclmissing"),
                Component.translatable("modid.config.yaclmissing.desc"),
                "https://modrinth.com/mod/yacl/",
                CommonComponents.GUI_BACK,
                true);
    }
}