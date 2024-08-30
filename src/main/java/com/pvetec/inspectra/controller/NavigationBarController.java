package com.pvetec.inspectra.controller;

import cn.hutool.core.io.FileUtil;
import com.pvetec.inspectra.pojo.CurrentTest;
import com.pvetec.inspectra.pojo.TestItem;
import com.pvetec.inspectra.transmission.Transmission;
import com.pvetec.inspectra.transmission.TransmissionFactory;
import com.pvetec.inspectra.transmission.TransmissionManager;
import com.pvetec.inspectra.transmission.listener.DeviceConnectionListener;
import com.pvetec.inspectra.utils.JsonBeanConverter;
import com.pvetec.inspectra.utils.LogUtils;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class NavigationBarController implements DeviceConnectionListener {

    public static final String TAG = NavigationBarController.class.getSimpleName();

    private FXMLLoader guideDialogLoader;

    private GuideDialogController guideDialogController;

    private final BooleanProperty deviceConnectedProperty = new SimpleBooleanProperty(false);

    @FXML
    private Circle statusIndicator;

    private TransmissionManager transmissionManager;

    @FXML
    private Label statusLabel;

    @FXML
    private void initialize() {
        try {

            CurrentTest currentTest = JsonBeanConverter.fileToBean("config/current_test.json", CurrentTest.class);

            transmissionManager = new TransmissionManager();

            transmissionManager.setCommunication(currentTest.getName(), this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            guideDialogLoader = new FXMLLoader(getClass().getResource("DialogView.fxml"));
            Parent dialogRoot = guideDialogLoader.load();
            guideDialogController = guideDialogLoader.getController();

        } catch (IOException e) {
            e.printStackTrace(); // Better error handling could be added here
        }
    }

    @FXML
    private void openDialog(ActionEvent event) {

        if (guideDialogController == null) {
            throw new IllegalStateException("Dialog controller not initialized.");
        }

        try {
            List<com.pvetec.inspectra.pojo.Platform> platforms = JsonBeanConverter.fileToBeanList("config/test_model.json", com.pvetec.inspectra.pojo.Platform.class);
            guideDialogController.setPlatformList(platforms);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create the dialog stage
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Dialog");
        dialogStage.initModality(Modality.APPLICATION_MODAL); // Make it modal
        Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        dialogStage.initOwner(primaryStage); // Set the owner

        // Get primary stage size
        double primaryWidth = primaryStage.getWidth();
        double primaryHeight = primaryStage.getHeight();

        // Set dialog size as a proportion of primary stage size
        double dialogWidth = primaryWidth * 0.5; // 50% of primary stage width
        double dialogHeight = primaryHeight * 0.5; // 50% of primary stage height

        Scene dialogScene = new Scene(guideDialogLoader.getRoot(), dialogWidth, dialogHeight);
        dialogStage.setScene(dialogScene);

        // Center the dialog on the primary stage
        dialogStage.centerOnScreen(); // Center the dialog on the screen
        dialogStage.setX(primaryStage.getX() + (primaryWidth - dialogWidth) / 2); // Center horizontally
        dialogStage.setY(primaryStage.getY() + (primaryHeight - dialogHeight) / 2); // Center vertically

        // Show the dialog and wait for it to close
        dialogStage.showAndWait();
    }

    /**
     * Called when a device is connected. Updates the status indicator and label.
     */
    @Override
    public void onDeviceConnected() {
        Platform.runLater(() -> {
            statusIndicator.setFill(Color.GREEN);
            statusLabel.setText("Device Connected");
            deviceConnectedProperty.set(true);
        });
    }

    /**
     * Called when a device is disconnected. Updates the status indicator and label.
     */
    @Override
    public void onDeviceDisconnected() {
        Platform.runLater(() -> {
            statusIndicator.setFill(Color.RED);
            statusLabel.setText("Device Disconnected");
            deviceConnectedProperty.set(false);
        });
    }

    /**
     * Returns the device connected property.
     *
     * @return the device connected property
     */
    public BooleanProperty deviceConnectedProperty() {
        return deviceConnectedProperty;
    }
}
