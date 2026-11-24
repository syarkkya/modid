package me.syarkkya.modid.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class ConfigLoaderClient {
    private static class HolderClient {
        public static final ConfigClassHandler<ConfigLoaderClient> HANDLER = ConfigClassHandler.createBuilder(ConfigLoaderClient.class)
                .id(ResourceLocation.fromNamespaceAndPath("modid", "config-client"))
                .serializer(config -> GsonConfigSerializerBuilder.create(config)
                        .setPath(FabricLoader.getInstance().getConfigDir().resolve("modid-client.json5"))
                        .build())
                .build();
    }

    @SerialEntry public boolean clientToggle = true;

    private static ConfigLoaderClient FALLBACK;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("modid-client.json5");

    public static ConfigLoaderClient getConfig() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            return HolderClient.HANDLER.instance();
        }
        return FALLBACK != null ? FALLBACK : (FALLBACK = new ConfigLoaderClient());
    }

    public static void init() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            HolderClient.HANDLER.load();
        } else if (Files.exists(PATH)) {
            try (Reader reader = Files.newBufferedReader(PATH)) {
                FALLBACK = GSON.fromJson(reader, ConfigLoaderClient.class);
            } catch (Exception ignored) {}
        }
    }

    public static ConfigClassHandler<ConfigLoaderClient> getHandler() {
        return HolderClient.HANDLER;
    }

    public static void save() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            HolderClient.HANDLER.save();
        }
    }
}