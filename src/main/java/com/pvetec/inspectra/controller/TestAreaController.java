package com.pvetec.inspectra.controller;

import com.pvetec.inspectra.interfaces.StationTestWorkflow;
import com.pvetec.inspectra.pojo.CurrentTest;
import com.pvetec.inspectra.pojo.SharedData;
import com.pvetec.inspectra.ui.SnWriterTestWorkflow;
import com.pvetec.inspectra.ui.VerificationNumberTestWorkflow;
import com.pvetec.inspectra.utils.JsonBeanConverter;
import com.pvetec.inspectra.utils.LogUtil;
import com.pvetec.inspectra.enums.StationEnum; // Assuming you have this enum
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TestAreaController {
    public static final String TAG = TestAreaController.class.getSimpleName();

    @FXML
    private VBox testAreaVBox; // Reference to the VBox in the FXML

    private SharedData sharedData;

    StationEnum stationEnum = null;

    StationTestWorkflow workflow = null;

    public void setSharedData(SharedData sharedData) {
        this.sharedData = sharedData;

        // Listen to device connection status
        this.sharedData.deviceConnectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                workflow.startTest();
            } else {
                workflow.resetTest();
            }
        });

        // Listen to operation finished status
        this.sharedData.operationFinishedProperty().addListener((observable, oldValue, newValue) -> {
            LogUtil.e(TAG, "Operation finished");
            // Update UI or handle post-operation tasks here
        });

        // Listen to station property changes
        this.sharedData.stationEnumSimpleObjectProperty().addListener((observable, oldStation, newStation) -> {
            LogUtil.e(TAG, "Station changed from " + oldStation + " to " + newStation);
            updateTestArea(newStation);
        });
    }

    @FXML
    private void initialize() {
        // Initial UI setup
        try {
            // Read the current test configuration from JSON file
            CurrentTest currentTest = JsonBeanConverter.fileToBean("config/currentTest.json", CurrentTest.class);

            // Log the current test station name
            LogUtil.highlight(TAG, "Current test station: " + currentTest.getStationName());

            // Map the station name to StationEnum
            StationEnum stationEnum = getStationEnumFromName(currentTest.getStationName());

            // Update the test area with the default or retrieved station
            updateTestArea(stationEnum);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read current test configuration", e);
        }
    }

    /**
     * Maps a station name to the corresponding StationEnum.
     *
     * @param stationName The name of the station.
     * @return The corresponding StationEnum value, or null if not found.
     */
    private StationEnum getStationEnumFromName(String stationName) {
        for (StationEnum station : StationEnum.values()) {
            if (station.getName().equalsIgnoreCase(stationName)) {
                return station;
            }
        }
        return null; // Return null if no matching station is found
    }

    private void updateTestArea(StationEnum station) {
        // Clear existing content in the testAreaVBox
        testAreaVBox.getChildren().clear();

        if (station == null) {
            return;
        }
        switch (station) {
            case SN_WRITER:
                workflow = new SnWriterTestWorkflow();
                break;
            case VERIFICATION_NUMBER:
                workflow = new VerificationNumberTestWorkflow();
                break;
            default:
                return;
        }
        workflow.createTestForm(testAreaVBox);
    }

    private void createSnWriterForm() {
        // Create a form layout
        VBox formVBox = new VBox(10.0);
        formVBox.setPadding(new Insets(10));

        // Create and add Serial Number input
        HBox serialNumberHBox = new HBox(10.0);
        Label serialNumberLabel = new Label("Serial Number:");
        TextField serialNumberField = new TextField();
        serialNumberField.setPromptText("Enter Serial Number");
        serialNumberHBox.getChildren().addAll(serialNumberLabel, serialNumberField);

        // Create and add Bluetooth Address input
        HBox bluetoothAddrHBox = new HBox(10.0);
        Label bluetoothAddrLabel = new Label("Bluetooth Address:");
        TextField bluetoothAddrField = new TextField();
        bluetoothAddrField.setPromptText("Enter Bluetooth Address");
        bluetoothAddrHBox.getChildren().addAll(bluetoothAddrLabel, bluetoothAddrField);

        // Create and add WiFi Address input
        HBox wifiAddrHBox = new HBox(10.0);
        Label wifiAddrLabel = new Label("WiFi Address:");
        TextField wifiAddrField = new TextField();
        wifiAddrField.setPromptText("Enter WiFi Address");
        wifiAddrHBox.getChildren().addAll(wifiAddrLabel, wifiAddrField);

        // Create and add Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> handleSubmit(serialNumberField, bluetoothAddrField, wifiAddrField));

        // Add all components to the form VBox
        formVBox.getChildren().addAll(serialNumberHBox, bluetoothAddrHBox, wifiAddrHBox, submitButton);

        // Add form VBox to the testAreaVBox
        testAreaVBox.getChildren().add(formVBox);
    }

    private void handleSubmit(TextField serialNumberField, TextField bluetoothAddrField, TextField wifiAddrField) {
        String serialNumber = serialNumberField.getText();
        String bluetoothAddr = bluetoothAddrField.getText();
        String wifiAddr = wifiAddrField.getText();

        // Handle the form submission
        LogUtil.e(TAG, "Serial Number: " + serialNumber);
        LogUtil.e(TAG, "Bluetooth Address: " + bluetoothAddr);
        LogUtil.e(TAG, "WiFi Address: " + wifiAddr);

        // Implement the logic to handle the form data here
    }
}
