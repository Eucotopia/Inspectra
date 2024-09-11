package com.pvetec.inspectra.controller;

import com.pvetec.inspectra.pojo.SharedData;
import com.pvetec.inspectra.utils.LogUtil;
import com.pvetec.inspectra.enums.StationEnum; // Assuming you have this enum
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TestAreaController {
    public static final String TAG = TestAreaController.class.getSimpleName();

    @FXML
    private Label welcomeText;

    @FXML
    private VBox testAreaVBox; // Reference to the VBox in the FXML

    private SharedData sharedData;

    public void setSharedData(SharedData sharedData) {
        this.sharedData = sharedData;

        // Listen to device connection status
        this.sharedData.deviceConnectedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                if (newValue) {
                    welcomeText.setText("Device Connected. Starting Test...");
                } else {
                    welcomeText.setText("Device Disconnected. Stopping Test...");
                }
            }
        });

        // Listen to operation finished status
        this.sharedData.operationFinishedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                LogUtil.e(TAG, "Operation finished");
                // Update UI or handle post-operation tasks here
            }
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
        updateTestArea(null); // Update test area with default or null station
    }

    private void updateTestArea(StationEnum station) {
        // Clear existing content in the testAreaVBox
        testAreaVBox.getChildren().clear();

        if (station == null) {
            welcomeText.setText("No Station Selected");
            return;
        }

        switch (station) {
            case SN_WRITER:
                welcomeText.setText("SN Writer Station");
                createSnWriterForm();
                break;

            case VERIFICATION_NUMBER:
                welcomeText.setText("Verification Number Station");
                // Add specific UI components for Verification Number Station
                Button verificationButton = new Button("Start Verification Test");
                verificationButton.setOnAction(event -> startTest());
                testAreaVBox.getChildren().add(verificationButton);
                break;

            default:
                welcomeText.setText("Unknown Station");
                break;
        }
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

    public void startTest() {
        // Logic to start the test
        LogUtil.e(TAG, "Starting test...");
        // Implement the test logic here
    }
}
