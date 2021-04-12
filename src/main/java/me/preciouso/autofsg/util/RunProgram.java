package me.preciouso.autofsg.util;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.lang3.SystemUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class RunProgram {
    private static final String executableName = "seed";
    public static String execCmd;
    private static boolean foundExec = false;
    private static final Path autofsgDir = Paths.get(FabricLoader.getInstance().getGameDir().toString(), "mods", "autofsg");

    private static String winToWSlPath(String winPath) {
        char driveLetter = winPath.charAt(0);
        String partialPath = winPath.substring(3);
        return "/mnt/" + Character.toLowerCase(driveLetter) + "/" + partialPath.replace("\\", "/");
    }

    // Find seed executable
    private static String getSeedExec() {
        Path myExec;

        if (autofsgDir.toFile().exists()) {

            myExec = Paths.get(autofsgDir.toString(), executableName);

            if (myExec.toFile().exists() && myExec.toFile().canExecute()) {
                foundExec = true;
                String cmd = myExec.toString();

                if (SystemUtils.IS_OS_WINDOWS) {
                    cmd = "bash.exe -c " + winToWSlPath(cmd);
                }

                return cmd;
            }
        }

        return null;
    }

    public static String[] run() throws IOException {
        // Check for executable path, throw error if still can't find it
        if (!foundExec) {
            execCmd = getSeedExec();
        }
        if (!foundExec || execCmd == null) {
            throw new FileNotFoundException("Could not find an *executable* file named " + executableName + " in " + autofsgDir);
        }

        // format: {seed, verification-code}
        String[] seedInfo = new String[3];

        // Run seed exec, using WSL if on windows
        ProcessBuilder builder = new ProcessBuilder(execCmd.split(" ")); // Split into array to prevent cmd not found on windows

        builder.redirectErrorStream(true);
        Process process = builder.start();
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;
        boolean collectVerification = false; // meaning verification will be on next line
        // begin to read output
        while ((line = reader.readLine()) != null) {
            // System.out.println("OUT: " + line); // debug ish
            seedInfo[2] = line; // also permanently here for debug purposes, usually to spit out an error

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
                collectVerification = false;
            } else if (ParseOutput.isSeedLine(line)) {
                seedInfo[0] = line.substring(6); // Seed: ...
            }
         }

        // Print seed and verification for log
        System.out.println(Arrays.toString(Arrays.copyOfRange(seedInfo, 0, 2)));
        return seedInfo;

    }
}
