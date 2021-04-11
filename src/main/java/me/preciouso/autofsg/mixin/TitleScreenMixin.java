package me.preciouso.autofsg.mixin;

import me.preciouso.autofsg.AutoFSG;
import me.preciouso.autofsg.screen.FSGScreen;
import me.preciouso.autofsg.util.RunProgram;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.level.LevelInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(at=@At("RETURN"), method="initWidgetsNormal")
    private void addFSGButton(int y, int spacingY, CallbackInfo ci) {
        // Add custom button
        // new TranslatableText("menu.singleplayer")
        this.addButton(new ButtonWidget(this.width / 2 - 100 + 205, y, 50, 20, new LiteralText("Auto FSG"), (buttonWidget) -> {
            // On button click:
            long seed = 0L;

            try {
                // Try running seed gen
                String[] lvlInfo = RunProgram.run();
                seed = Long.parseLong(lvlInfo[0]);
                AutoFSG.setVerificationCode(lvlInfo[1]);  // TODO hacky, set as world nbt attribute?
            } catch (IOException e) {
                // Needs seed, return if none
                e.printStackTrace();
                return;
            }

            // Preload World generation info
            LevelInfo lvl = new LevelInfo("New World", GameMode.SURVIVAL, false, Difficulty.EASY, false,
                    new GameRules(), DataPackSettings.SAFE_MODE);

            DynamicRegistryManager.Impl impl = DynamicRegistryManager.create();

            Registry<DimensionType> dimensionTypeRegistry = impl.get(Registry.DIMENSION_TYPE_KEY);
            Registry<Biome> registry2 = impl.get(Registry.BIOME_KEY);
            Registry<ChunkGeneratorSettings> registry3 = impl.get(Registry.NOISE_SETTINGS_WORLDGEN);

            SimpleRegistry<DimensionOptions> simpleRegistry = GeneratorOptions.method_28608(dimensionTypeRegistry, DimensionType.createDefaultDimensionOptions(dimensionTypeRegistry, registry2, registry3, seed),
                    GeneratorOptions.createOverworldGenerator(registry2, registry3, seed));

            GeneratorOptions gen = new GeneratorOptions(seed, true, false, simpleRegistry);

            // Open CreateWorldScreen with generator options loaded
            FSGScreen fsg = new FSGScreen(this, lvl, gen, null, DataPackSettings.SAFE_MODE, impl);
            MinecraftClient.getInstance().openScreen(fsg);

        }));
    }
}
