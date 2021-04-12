package me.preciouso.autofsg.mixin;

import com.google.common.base.Strings;
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
import net.minecraft.util.registry.RegistryTracker;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
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
            String[] lvlInfo;
            long seed = 0L;
            String verificationCode;

            try {
                // Try running seed gen
                System.out.println("Getting seed and verification...");
                lvlInfo = RunProgram.run();
            } catch (IOException e) {
                // Needs seed, return if none
                e.printStackTrace();
                return;
            }

            verificationCode = lvlInfo[1];
            // return if can't find a verification code
            if (Strings.isNullOrEmpty(verificationCode)) {
                System.out.println("Program last output: " + lvlInfo[2]);
                return;
            }

            seed = Long.parseLong(lvlInfo[0]);

            AutoFSG.setVerificationCode(verificationCode);

            // Preload World generation info
            LevelInfo lvl = new LevelInfo("New World", GameMode.SURVIVAL, false, Difficulty.EASY, false,
                    new GameRules(), DataPackSettings.SAFE_MODE);

            RegistryTracker.Modifiable mods = DimensionType.addRegistryDefaults(RegistryTracker.create());
            GeneratorOptions genOpts = new GeneratorOptions(seed, true, false,
                    GeneratorOptions.method_28608(DimensionType.method_28517(seed), GeneratorOptions.createOverworldGenerator(seed)));

            FSGScreen fsg = new FSGScreen(this, lvl, genOpts, null, mods);

            // Preload World generation info

            MinecraftClient.getInstance().openScreen(fsg);
        }));
    }
}
