package com.pvetec.inspectra.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ADBUtil {

    /**
     * Executes the specified ADB command and returns the output as a list of strings.
     * @param command The ADB command to execute.
     * @return The output of the command.
     * @throws Exception Throws an exception if there's an error executing the command.
     */
    private static List<String> executeCommand(String command) throws Exception {
        List<String> output = new ArrayList<>();
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.add(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Command execution failed with exit code " + exitCode);
        }

        return output;
    }

    /**
     * Gets the first connected ADB device.
     * @return The ID of the first connected device.
     */
    //TODO 考虑 offline
    public static String getFirstAdbDevice() {
        List<String> deviceList;
        try {
            deviceList = executeCommand("adb devices");
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute adb devices command", e);
        }

        return deviceList.stream()
                .filter(line -> line.endsWith("device"))
                .map(line -> line.split("\\s")[0])
                .findFirst()
                // return null if no devices are found
                .orElse(null);
    }

    /**
     * Restarts the adb daemon with root permissions.
     * @return The output of the command.
     * @throws Exception Throws an exception if there's an error executing the command.
     */
    public static List<String> adbRoot() throws Exception {
        return executeCommand("adb root");
    }

    /**
     * Remounts the /system partition on the device in read-write mode.
     * @return The output of the command.
     * @throws Exception Throws an exception if there's an error executing the command.
     */
    public static List<String> adbRemount() throws Exception {
        return executeCommand("adb remount");
    }

    /**
     * Sends a specified ADB command.
     * @param command The command to send (e.g., "shell ls /system").
     * @return The output of the command.
     * @throws Exception Throws an exception if there's an error executing the command.
     */
    public static List<String> adbCommand(String command) throws Exception {
        return executeCommand("adb " + command);
    }
}
