package com.pvetec.inspectra.controller;

import com.pvetec.inspectra.MainController;
import com.pvetec.inspectra.enums.StationEnum;
import com.pvetec.inspectra.manager.ConfigManager;
import com.pvetec.inspectra.pojo.CurrentTest;
import com.pvetec.inspectra.pojo.SharedData;
import com.pvetec.inspectra.transmission.TransmissionManager;
import com.pvetec.inspectra.transmission.listener.DeviceConnectionListener;
import com.pvetec.inspectra.utils.JsonBeanConverter;
import com.pvetec.inspectra.utils.LogUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * @author LIWEI
 */
public class NavigationBarController implements DeviceConnectionListener {

    public static final String TAG = NavigationBarController.class.getSimpleName();

    @FXML
    private MenuButton stationMenuButton;

    private FXMLLoader guideDialogLoader;

    private SharedData sharedData;

    private GuideDialogController guideDialogController;


    @FXML
    private Circle statusIndicator;

    private TransmissionManager transmissionManager;

    @FXML
    private Label statusLabel;

    @FXML
    private Tooltip statusLabelTooltip;

    public void setSharedData(SharedData sharedData) {
        this.sharedData = sharedData;

        CurrentTest currentTest = ConfigManager.getCurrentTest();

        String stationName = currentTest.getStationName();

        for (MenuItem item : stationMenuButton.getItems()) {
            StationEnum stationEnum = StationEnum.fromName(item.getText());
            if (stationEnum != null) {
                if (stationName.equals(stationEnum.getName())) {
                    item.setDisable(true);
                    handleStationSelection(stationEnum);
                }
            }
        }
    }

    @FXML
    private void initialize() {
        initStationMenu();
        // Check if Tooltip is injected correctly
        if (statusLabel.getTooltip() != null) {
            statusLabelTooltip = statusLabel.getTooltip();
        } else {
            // If Tooltip is not set in FXML, create one dynamically
            statusLabelTooltip = new Tooltip("Serial Number: null");
            statusLabel.setTooltip(statusLabelTooltip);
        }

        try {
            CurrentTest currentTest = ConfigManager.getCurrentTest();

            transmissionManager = new TransmissionManager();

            transmissionManager.setCommunication(currentTest.getProjectName(), this);

            transmissionManager.openConnection();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {

            guideDialogLoader = new FXMLLoader(getClass().getResource("DialogView.fxml"));

            Parent dialogRoot = guideDialogLoader.load();

            guideDialogController = guideDialogLoader.getController();

            guideDialogController.setSharedData(MainController.sharedData);

        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage());
        }
    }

    private void initStationMenu() {
        // Clear existing items
        stationMenuButton.getItems().clear();
        // Iterate over StationEnum values and create MenuItem for each
        for (StationEnum station : StationEnum.values()) {
            MenuItem menuItem = new MenuItem(station.getName());
//                menuItem.setUserData(station);
            menuItem.setOnAction(e -> handleStationSelection(station));
            stationMenuButton.getItems().add(menuItem);
        }
    }

    private void handleStationSelection(StationEnum station) {
        for (MenuItem item : stationMenuButton.getItems()) {
            item.setDisable(false);
        }

        for (MenuItem item : stationMenuButton.getItems()) {
            StationEnum stationEnum = StationEnum.fromName(item.getText());
            if (stationEnum != null && stationEnum.equals(station)) {
                item.setDisable(true);
                sharedData.setStationProperty(station.getCode());
//                sharedData.setStationEnumProperty(station);
            }
        }
    }


    @FXML
    private void openDialog(ActionEvent event) {

        if (guideDialogController == null) {
            throw new IllegalStateException("Dialog controller not initialized.");
        }

        List<com.pvetec.inspectra.pojo.Platform> platforms;

        try {
            platforms = JsonBeanConverter.fileToBeanList("config/test_model.json", com.pvetec.inspectra.pojo.Platform.class);
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

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DialogView.fxml"));
            Parent root = loader.load();

            GuideDialogController newController = loader.getController();
            newController.setSharedData(MainController.sharedData);
            newController.setPlatformList(platforms);

            Scene dialogScene = new Scene(root, dialogWidth, dialogHeight);
            dialogStage.setScene(dialogScene);

            // Center the dialog on the primary stage
            dialogStage.setX(primaryStage.getX() + (primaryWidth - dialogWidth) / 2);
            dialogStage.setY(primaryStage.getY() + (primaryHeight - dialogHeight) / 2);

            dialogStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Called when a device is connected. Updates the status indicator and label.
     */
    @Override
    public void onDeviceConnected(String serialNumber) {
        Platform.runLater(() -> {
            statusIndicator.setFill(Color.GREEN);
            statusLabel.setText("Device Connected");
            // Create and set Tooltip for statusLabel
            setTooltipText(serialNumber);
            sharedData.setDeviceConnected(true);
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
            statusLabelTooltip.setText("Serial Number: null");
            // Notify SharedData
            sharedData.setDeviceConnected(false);
        });
    }

    /**
     * 动态设置 Tooltip 的文本内容
     */
    public void setTooltipText(String newText) {
        if (statusLabelTooltip != null) {
            statusLabelTooltip.setText("Serial Number: " + newText);
        }
    }

    @FXML
    private void showAbout(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // 创建对话框的舞台
        Stage dialogStage = new Stage();
        dialogStage.setTitle("About Inspectra");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);

        // 获取主窗口的尺寸
        double primaryWidth = primaryStage.getWidth();
        double primaryHeight = primaryStage.getHeight();
        double dialogWidth = primaryWidth * 0.4; // 设置对话框的宽度为主窗口宽度的 40%
        double dialogHeight = primaryHeight * 0.4; // 设置对话框的高度为主窗口高度的 40%

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pvetec/inspectra/controller/AboutDialog.fxml"));
            VBox content = loader.load();

            Scene dialogScene = new Scene(content, dialogWidth, dialogHeight);
            dialogStage.setScene(dialogScene);

            // 将对话框居中显示在主窗口上
            dialogStage.setX(primaryStage.getX() + (primaryWidth - dialogWidth) / 2);
            dialogStage.setY(primaryStage.getY() + (primaryHeight - dialogHeight) / 2);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void openLogFolder(ActionEvent event) {
        File logDir = new File("logs");

        if (logDir.exists() && logDir.isDirectory()) {
            try {
                Desktop.getDesktop().open(logDir);
            } catch (IOException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Open Log Folder Failed");
                errorAlert.setContentText("Could not open log folder: " + e.getMessage());
                errorAlert.showAndWait();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Log Folder Not Found");
            errorAlert.setContentText("The specified log folder does not exist.");
            errorAlert.showAndWait();
        }
    }
}
