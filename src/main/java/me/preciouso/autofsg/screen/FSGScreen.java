package me.preciouso.autofsg.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

// Only exists to differentiate custom CreateWorldScreen from vanilla, and for mixin
public class FSGScreen extends CreateWorldScreen {

    public FSGScreen(@Nullable Screen parent, LevelInfo levelInfo, GeneratorOptions generatorOptions, @Nullable Path dataPackTempDir, DataPackSettings dataPackSettings, DynamicRegistryManager.Impl registryManager) {
        super(parent, levelInfo, generatorOptions, dataPackTempDir, dataPackSettings, registryManager);
    }

}
