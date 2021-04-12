package me.preciouso.autofsg;

import net.fabricmc.api.ModInitializer;

public class AutoFSG implements ModInitializer {
	private static String verificationCode = null;

	@Override
	public void onInitialize() {
		System.out.println("Auto FSG Starting");
	}

	public static void setVerificationCode(String verificationCode) {
		AutoFSG.verificationCode = verificationCode;
	}

	public static String getVerificationCode() {
		return verificationCode;
	}
}
