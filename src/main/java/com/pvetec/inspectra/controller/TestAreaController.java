package com.pvetec.inspectra.controller;

import com.pvetec.inspectra.enums.StationEnum;
import com.pvetec.inspectra.pojo.SharedData;
import com.pvetec.inspectra.ui.WorkFlowManager;
import com.pvetec.inspectra.utils.LogUtil;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * @author LIWEI
 */
public class TestAreaController {
    public static final String TAG = TestAreaController.class.getSimpleName();
    // Reference to the VBox in the FXML
    @FXML
    private VBox testAreaVBox;

    private SharedData sharedData;

    private WorkFlowManager workFlowManager;

    public void setSharedData(SharedData sharedData) {
        this.sharedData = sharedData;

        // Listen to device connection status
        this.sharedData.deviceConnectedProperty().addListener((observable, oldValue, newValue) -> {

            LogUtil.highlight(TAG, "Device status changed from " + (oldValue ? "connected" : "disconnected") + " to " + (newValue ? "connected" : "disconnected"));

            if (newValue) {
                workFlowManager.startTest();
            } else {
                workFlowManager.resetTest();
            }
        });

        // Listen to operation finished status
        this.sharedData.operationFinishedProperty().addListener((observable, oldValue, newValue) -> {
            LogUtil.e(TAG, "Operation finished");
            // Update UI or handle post-operation tasks here
        });

        // Listen to station property changes
        this.sharedData.stationProperty().addListener((observable, oldStation, newStation) -> {
            LogUtil.highlight(TAG, "Station changed from " + oldStation + " to " + newStation);
            updateTestArea(newStation.intValue());
        });
    }

    @FXML
    private void initialize() {
        // Initialize workflow manager (you might want to pass it in via dependency injection or another method)
        this.workFlowManager = new WorkFlowManager();

    }


    private void updateTestArea(Integer station) {
        // Clear existing content in the testAreaVBox
        testAreaVBox.getChildren().clear();

        if (station == null || station == 0) {
            return;
        }

        workFlowManager.setStationTestWorkflow(station);

        workFlowManager.initializeTestArea(testAreaVBox);
    }
}
