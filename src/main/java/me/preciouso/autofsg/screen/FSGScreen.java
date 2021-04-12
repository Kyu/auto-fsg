package me.preciouso.autofsg.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.util.registry.RegistryTracker;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

// Only exists to differentiate custom CreateWorldScreen from vanilla, and for mixin
public class FSGScreen extends CreateWorldScreen {
    public FSGScreen(@Nullable Screen screen, LevelInfo levelInfo, GeneratorOptions generatorOptions, @Nullable Path path, RegistryTracker.Modifiable modifiable) {
        super(screen, levelInfo, generatorOptions, path, modifiable);
    }
}
