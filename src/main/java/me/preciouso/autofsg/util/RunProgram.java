package me.preciouso.autofsg.util;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.lang3.SystemUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class RunProgram {
    public static String seedPath;
    private static boolean foundExec = false;
    private static final Path autofsgDir = Paths.get(FabricLoader.getInstance().getGameDir().toString(), "mods", "autofsg");

    // Find seed executable
    private static String getSeedExec() {
        Path myExec;

        if (autofsgDir.toFile().exists()) {
            if (SystemUtils.IS_OS_WINDOWS) {
                myExec = Paths.get(autofsgDir.toString(), "seed.exe");
            } else {
                myExec = Paths.get(autofsgDir.toString(), "seed");
            }

            if (myExec.toFile().exists() && myExec.toFile().canExecute()) {
                foundExec = true;
                return myExec.toString();
            }
        }

        return null;
    }

    public static String[] run() throws IOException {
        // Check for executable path, throw error if still can't find it
        if (!foundExec) {
            seedPath = getSeedExec();
        }
        if (!foundExec) {
            throw new FileNotFoundException("Could not find an *executable* file named seed or seed.exe in " + autofsgDir.toString());
        }

        // foramt: {seed, verification-code}
        String[] seedInfo = new String[2];

        // Run seed exec
        ProcessBuilder builder = new ProcessBuilder(seedPath);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;
        boolean collectVerification = false; // meaning verification will be on next line
        // begin to read output
        while ((line = reader.readLine()) != null) {
            // System.out.println(line);

            /*
            Expects:

            Seed: xxxx
            Verification Code:
            xxxx-yyy-zzz

            In that order
             */
            if (ParseOutput.isVerificationLine(line)) {
                collectVerification = true;
            } else if (collectVerification) {
                seedInfo[1] = line;
            } else if (ParseOutput.isSeedLine(line)) {
                seedInfo[0] = line.substring(6); // Seed: ...
            }
         }

        System.out.println(Arrays.toString(seedInfo));
        return seedInfo;

    }
}
