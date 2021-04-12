package me.preciouso.autofsg.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.network.MessageType;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.preciouso.autofsg.AutoFSG.getVerificationCode;

@Mixin(CreateWorldScreen.class)
public abstract class FSGScreenMixin {
    @Shadow private ButtonWidget moreOptionsButton;

    // To disable checking world options, particularly the seed
    @Inject(at=@At("RETURN"), method="init")
    private void disableButtons(CallbackInfo ci) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        // System.out.println(stackTraceElements[5].getMethodName()); // == md405422$lambda$addFSGButton$0$0 for FSG
        boolean isFsgScreen = stackTraceElements[5].getMethodName().contains("FSG");  // TODO needs to be less hacky, get instanceof somehow
        if (isFsgScreen) {
            this.moreOptionsButton.active = false;
        }
    }

    @Inject(at=@At("RETURN"), method="createLevel")
    private void shoutVerification(CallbackInfo ci) {
        String code = getVerificationCode();
        MinecraftClient mc = MinecraftClient.getInstance();
        if (code == null || mc.inGameHud == null) {
            return;
        }

        Text text = Texts.bracketed((new LiteralText(code)).styled((style) -> style.withColor(Formatting.GREEN)
                .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, code))
                .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("chat.copy.click")))
                .withInsertion(code)));

        mc.inGameHud.addChatMessage(MessageType.SYSTEM, text, null);
    }

}
