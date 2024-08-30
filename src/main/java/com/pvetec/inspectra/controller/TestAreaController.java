package com.pvetec.inspectra.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Controller class for handling the test area.
 */
public class TestAreaController {

    @FXML
    private Label welcomeText;

    private NavigationBarController navigationBarController;

    @FXML
    private void initialize() {
        // Set the text for the Label dynamically
        welcomeText.setText("Welcome to the Test Area!");
        try {

            //FXMLLoader必须使用参数初始化，否则getController会失败
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pvetec/inspectra/NavigationBar.fxml"));
            Parent root = loader.load();
            // Get the NavigationBarController instance

            navigationBarController = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Listen for device connection changes
        BooleanProperty deviceConnectedProperty = navigationBarController.deviceConnectedProperty();

        deviceConnectedProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    // Device disconnected, update the welcome text
                    welcomeText.setText("Device Disconnected");
                }
            }
        });
    }
}
