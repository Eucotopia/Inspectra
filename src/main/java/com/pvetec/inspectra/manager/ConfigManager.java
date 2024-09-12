package com.pvetec.inspectra.manager;

import com.pvetec.inspectra.constants.FilePathConstant;
import com.pvetec.inspectra.pojo.CurrentTest;
import com.pvetec.inspectra.pojo.SerialPortConfig;
import com.pvetec.inspectra.pojo.VerificationValues;
import com.pvetec.inspectra.utils.JsonBeanConverter;
import cn.hutool.core.io.FileUtil;

import java.io.IOException;

/**
 * Manages the configuration loading and updating for SerialPortConfig, VerificationValues, and CurrentTest.
 * <p>
 * Author: LIWEI
 */
public class ConfigManager {

    // Volatile keyword ensures visibility of changes to verificationValues, serialPortConfig, and currentTest across threads
    private static volatile VerificationValues verificationValues;
    private static volatile SerialPortConfig serialPortConfig;
    private static volatile CurrentTest currentTest;

    /**
     * Lazy loading mechanism with caching for SerialPortConfig. Loads the configuration from the JSON file
     * the first time it is requested. Uses double-checked locking to ensure thread safety and avoid
     * unnecessary synchronization overhead on subsequent calls.
     *
     * @return The current SerialPortConfig.
     */
    public static SerialPortConfig getSerialPortConfig() {
        if (serialPortConfig == null) {
            synchronized (ConfigManager.class) {
                if (serialPortConfig == null) {
                    try {
                        // Load JSON and convert it to a Java object
                        serialPortConfig = JsonBeanConverter.fileToBean(FilePathConstant.SERIAL_PORT_CONFIG, SerialPortConfig.class);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to load serial port configuration.", e);
                    }
                }
            }
        }
        return serialPortConfig;
    }

    /**
     * Lazy loading mechanism with caching for VerificationValues. Loads the configuration from the JSON file
     * the first time it is requested. Uses double-checked locking to ensure thread safety and avoid
     * unnecessary synchronization overhead on subsequent calls.
     *
     * @return The current VerificationValues.
     */
    public static VerificationValues getVerificationValues() {
        if (verificationValues == null) {
            synchronized (ConfigManager.class) { // Lock to ensure thread safety
                if (verificationValues == null) { // Double-check to avoid multiple initializations
                    try {
                        // Load JSON and convert it to a Java object
                        verificationValues = JsonBeanConverter.fileToBean(FilePathConstant.VERIFICATION_VALUES, VerificationValues.class);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to load verification values.", e);
                    }
                }
            }
        }
        return verificationValues;
    }

    /**
     * Lazy loading mechanism with caching for CurrentTest. Loads the configuration from the JSON file
     * the first time it is requested. Uses double-checked locking to ensure thread safety and avoid
     * unnecessary synchronization overhead on subsequent calls.
     *
     * @return The current CurrentTest.
     */
    public static CurrentTest getCurrentTest() {
        if (currentTest == null) {
            synchronized (ConfigManager.class) {
                if (currentTest == null) {
                    try {
                        // Load JSON and convert it to a Java object
                        currentTest = JsonBeanConverter.fileToBean(FilePathConstant.CURRENT_TEST, CurrentTest.class);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to load current test configuration.", e);
                    }
                }
            }
        }
        return currentTest;
    }

    /**
     * Manually refreshes the SerialPortConfig by reloading it from the JSON file. Useful if the JSON file
     * has been updated externally and the in-memory cache needs to be refreshed.
     */
    public static void reloadSerialPortConfig() {
        synchronized (ConfigManager.class) {
            try {
                // Reload JSON and update serialPortConfig
                serialPortConfig = JsonBeanConverter.fileToBean(FilePathConstant.SERIAL_PORT_CONFIG, SerialPortConfig.class);
            } catch (IOException e) {
                throw new RuntimeException("Failed to reload serial port configuration.", e);
            }
        }
    }

    /**
     * Manually refreshes the VerificationValues by reloading it from the JSON file. Useful if the JSON file
     * has been updated externally and the in-memory cache needs to be refreshed.
     */
    public static void reloadVerificationValues() {
        synchronized (ConfigManager.class) {
            try {
                // Reload JSON and update verificationValues
                verificationValues = JsonBeanConverter.fileToBean(FilePathConstant.VERIFICATION_VALUES, VerificationValues.class);
            } catch (IOException e) {
                throw new RuntimeException("Failed to reload verification values.", e);
            }
        }
    }

    /**
     * Manually refreshes the CurrentTest by reloading it from the JSON file. Useful if the JSON file
     * has been updated externally and the in-memory cache needs to be refreshed.
     */
    public static void reloadCurrentTest() {
        synchronized (ConfigManager.class) {
            try {
                // Reload JSON and update currentTest
                currentTest = JsonBeanConverter.fileToBean(FilePathConstant.CURRENT_TEST, CurrentTest.class);
            } catch (IOException e) {
                throw new RuntimeException("Failed to reload current test configuration.", e);
            }
        }
    }

    /**
     * Updates the SerialPortConfig object and persists the changes back to the JSON file. The new values are
     * saved to both the in-memory cache and written to the file for permanent storage.
     *
     * @param newConfig The updated SerialPortConfig to be saved.
     */
    public static void updateSerialPortConfig(SerialPortConfig newConfig) {
        synchronized (ConfigManager.class) {
            // Update the in-memory serialPortConfig
            serialPortConfig = newConfig;

            // Convert updated SerialPortConfig to JSON and write it to the file
            String jsonString = JsonBeanConverter.beanToJson(newConfig);
            FileUtil.writeUtf8String(jsonString, FilePathConstant.SERIAL_PORT_CONFIG); // Write to file
        }
    }

    /**
     * Updates the VerificationValues object and persists the changes back to the JSON file. The new values are
     * saved to both the in-memory cache and written to the file for permanent storage.
     *
     * @param newValues The updated VerificationValues to be saved.
     */
    public static void updateVerificationValues(VerificationValues newValues) {
        synchronized (ConfigManager.class) {
            // Update the in-memory verificationValues
            verificationValues = newValues;

            // Convert updated VerificationValues to JSON and write it to the file
            String jsonString = JsonBeanConverter.beanToJson(newValues);
            FileUtil.writeUtf8String(jsonString, FilePathConstant.VERIFICATION_VALUES); // Write to file
        }
    }

    /**
     * Updates the CurrentTest object and persists the changes back to the JSON file. The new values are
     * saved to both the in-memory cache and written to the file for permanent storage.
     *
     * @param newTest The updated CurrentTest to be saved.
     */
    public static void updateCurrentTest(CurrentTest newTest) {
        synchronized (ConfigManager.class) {
            // Update the in-memory currentTest
            currentTest = newTest;

            // Convert updated CurrentTest to JSON and write it to the file
            String jsonString = JsonBeanConverter.beanToJson(newTest);
            FileUtil.writeUtf8String(jsonString, FilePathConstant.CURRENT_TEST); // Write to file
        }
    }
}