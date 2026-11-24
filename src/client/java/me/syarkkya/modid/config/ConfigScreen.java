package me.syarkkya.modid.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class ConfigScreen {

    public static Screen createScreen(Screen parent) {
        return YetAnotherConfigLib.create(ConfigLoader.getHandler(), (defaults, config, builder) -> {
            ConfigLoaderClient clientConfig = ConfigLoaderClient.getConfig();
            ConfigLoaderClient clientDefaults = ConfigLoaderClient.getHandler().defaults();

            return builder
                    .title(Component.translatable("modid.config.title"))
                    .category(ConfigCategory.createBuilder()
                            .name(Component.translatable("modid.config.server"))
                            .tooltip(Component.translatable("modid.config.server.tooltip"))
                            .option(Option.<Boolean>createBuilder()
                                    .name(Component.translatable("modid.config.server.option"))
                                    .description(OptionDescription.of(Component.translatable("modid.config.server.option.desc")))
                                    .binding(defaults.serverToggle, () -> config.serverToggle, (val) -> config.serverToggle = val)
                                    .controller((opt) -> BooleanControllerBuilder.create(opt)
                                            .formatValue((val) -> val ? Component.translatable("modid.config.server.option.on") : Component.translatable("modid.config.server.option.off"))
                                            .coloured(true))
                                    .build())
                            .build())
                    .category(ConfigCategory.createBuilder()
                            .name(Component.translatable("modid.config.client"))
                            .tooltip(Component.translatable("modid.config.client.tooltip"))
                            .option(Option.<Boolean>createBuilder()
                                    .name(Component.translatable("modid.config.client.option"))
                                    .description(OptionDescription.of(Component.translatable("modid.config.client.option.desc")))
                                    .binding(clientDefaults.clientToggle, () -> clientConfig.clientToggle, (val) -> clientConfig.clientToggle = val)
                                    .controller((opt) -> BooleanControllerBuilder.create(opt)
                                            .formatValue((val) -> val ? Component.translatable("modid.config.client.option.on") : Component.translatable("modid.config.client.option.off"))
                                            .coloured(true))
                                    .build())
                            .build())
                    .save(() -> {
                        ConfigLoader.save();
                        ConfigLoaderClient.save();
                    });
        }).generateScreen(parent);
    }
}