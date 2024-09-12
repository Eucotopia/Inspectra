package com.pvetec.inspectra.ui;

import com.pvetec.inspectra.interfaces.StationTestWorkflow;
import com.pvetec.inspectra.pojo.TestItem;
import com.pvetec.inspectra.utils.LogUtil;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class SnWriterTestWorkflow implements StationTestWorkflow {

    public static final String TAG = SnWriterTestWorkflow.class.getSimpleName();


    @Override
    public void startTest() {
        LogUtil.e(TAG, "Starting SN Writer test...");
        // SN Writer test logic
    }

    @Override
    public void createTestArea(VBox vbox) {
        // Clear existing content in the VBox
        vbox.getChildren().clear();

        // Create and add form components
        createSnWriterForm(vbox);
    }

    @Override
    public void resetTest() {

    }

    private void createSnWriterForm(VBox vbox) {
        // Create a form layout
        VBox formVBox = new VBox(10.0);
        formVBox.setPadding(new javafx.geometry.Insets(10));

        // Create and add Serial Number input
        HBox serialNumberHBox = new HBox(10.0);
        Label serialNumberLabel = new Label("Serial Number:");
        TextField serialNumberField = new TextField();
        serialNumberField.setPromptText("Enter Serial Number");
        serialNumberHBox.getChildren().addAll(serialNumberLabel, serialNumberField);

        // Create and add WiFi Address input
        HBox wifiAddrHBox = new HBox(10.0);
        Label wifiAddrLabel = new Label("WiFi Address:");
        TextField wifiAddrField = new TextField();
        wifiAddrField.setPromptText("Enter WiFi Address");
        wifiAddrHBox.getChildren().addAll(wifiAddrLabel, wifiAddrField);

        // Create and add Bluetooth Address input
        HBox bluetoothAddrHBox = new HBox(10.0);
        Label bluetoothAddrLabel = new Label("Bluetooth Address:");
        TextField bluetoothAddrField = new TextField();
        bluetoothAddrField.setPromptText("Enter Bluetooth Address");
        bluetoothAddrHBox.getChildren().addAll(bluetoothAddrLabel, bluetoothAddrField);

        // Create and add IMEI_1 input
        HBox imei1HBox = new HBox(10.0);
        Label imei1Label = new Label("IMEI_1:");
        TextField imei1Field = new TextField();
        imei1Field.setPromptText("Enter IMEI_1");
        imei1HBox.getChildren().addAll(imei1Label, imei1Field);

        // Create and add IMEI_2 input
        HBox imei2HBox = new HBox(10.0);
        Label imei2Label = new Label("IMEI_2:");
        TextField imei2Field = new TextField();
        imei2Field.setPromptText("Enter IMEI_2");
        imei2HBox.getChildren().addAll(imei2Label, imei2Field);

        // Create and add Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> handleSubmit(
                serialNumberField.getText(),
                wifiAddrField.getText(),
                bluetoothAddrField.getText(),
                imei1Field.getText(),
                imei2Field.getText()
        ));

        // Add all components to the form VBox
        formVBox.getChildren().addAll(
                serialNumberHBox,
                wifiAddrHBox,
                bluetoothAddrHBox,
                imei1HBox,
                imei2HBox,
                submitButton
        );

        // Add form VBox to the main VBox
        vbox.getChildren().add(formVBox);
    }

    private void handleSubmit(String serialNumber, String wifiAddr, String bluetoothAddr, String imei1, String imei2) {
        // Handle the form submission
        LogUtil.e(TAG, "Serial Number: " + serialNumber);
        LogUtil.e(TAG, "WiFi Address: " + wifiAddr);
        LogUtil.e(TAG, "Bluetooth Address: " + bluetoothAddr);
        LogUtil.e(TAG, "IMEI_1: " + imei1);
        LogUtil.e(TAG, "IMEI_2: " + imei2);

        // Implement the logic to handle the form data here
        // For example, send this data to a server or process it locally
    }
}
