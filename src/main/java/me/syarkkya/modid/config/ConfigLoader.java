package me.syarkkya.modid.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

public class ConfigLoader {
    private static class Holder {
        public static final ConfigClassHandler<ConfigLoader> HANDLER = ConfigClassHandler.createBuilder(ConfigLoader.class)
                .id(ResourceLocation.fromNamespaceAndPath("modid", "config"))
                .serializer(config -> GsonConfigSerializerBuilder.create(config)
                        .setPath(FabricLoader.getInstance().getConfigDir().resolve("modid.json5"))
                        .build())
                .build();
    }

    @SerialEntry public boolean serverToggle = true;

    private static ConfigLoader FALLBACK;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("modid.json5");

    public static ConfigLoader getConfig() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            return Holder.HANDLER.instance();
        }
        return FALLBACK != null ? FALLBACK : (FALLBACK = new ConfigLoader());
    }

    public static void init() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            Holder.HANDLER.load();
        } else if (Files.exists(PATH)) {
            try (Reader reader = Files.newBufferedReader(PATH)) {
                FALLBACK = GSON.fromJson(reader, ConfigLoader.class);
            } catch (Exception ignored) {}
        }
    }

    public static ConfigClassHandler<ConfigLoader> getHandler() {
        return Holder.HANDLER;
    }

    public static void save() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            Holder.HANDLER.save();
        }
    }
}