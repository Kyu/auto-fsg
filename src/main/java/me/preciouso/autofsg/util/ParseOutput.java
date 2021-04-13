package me.preciouso.autofsg.util;

public class ParseOutput {
    static boolean isSeedLine(String line) {
        return line.contains("Seed");
    }

    static boolean isVerificationLine(String line) {
        return line.contains("Verification Token");
    }
}
