package me.preciouso.autofsg;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.literal;

public class AutoFSG implements ModInitializer {
	private static String verificationCode = null;

	@Override
	public void onInitialize() {
		System.out.println("Auto FSG Starting");

		// Registers command that outputs a copyable verification code
		// Copied from net.mc.server.command.SeedCommand
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			dispatcher.register(literal("verification").executes(context -> {

				Text text = Texts.bracketed((new LiteralText(getVerificationCode())).styled((style) -> {
					return style.withColor(Formatting.GREEN).withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, String.valueOf(getVerificationCode()))).withHoverEvent(new HoverEvent(net.minecraft.text.HoverEvent.Action.SHOW_TEXT, new TranslatableText("chat.copy.click"))).withInsertion(getVerificationCode());
				}));


				context.getSource().sendFeedback(text, false);
				return 1;
			}));
		});
	}

	public static void setVerificationCode(String verificationCode) {
		AutoFSG.verificationCode = verificationCode;
	}

	public String getVerificationCode() {
		return verificationCode;
	}
}
