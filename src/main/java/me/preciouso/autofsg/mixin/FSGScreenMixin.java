package me.preciouso.autofsg.mixin;

import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class FSGScreenMixin {
    @Shadow private ButtonWidget moreOptionsButton;

    // To disable checking world options, particularly the seed
    @Inject(at=@At("RETURN"), method="init")
    private void disableButtons(CallbackInfo ci) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        // System.out.println(stackTraceElements[5].getMethodName()); // == md405422$lambda$addFSGButton$0$0 for FSG
        // System.out.println(stackTraceElements[5].hashCode());  // == -409647511 for md405422$lambda$addFSGButton$0$0
        boolean a = stackTraceElements[5].getMethodName().contains("FSG");  // TODO needs to be less hacky, get instanceof somehow
        if (a) {
            this.moreOptionsButton.active = false;
        }
    }
}
