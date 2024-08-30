package com.pvetec.inspectra.controller;

import cn.hutool.core.io.FileUtil;
import com.pvetec.inspectra.pojo.PlatformType;
import com.pvetec.inspectra.utils.LogUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class GuideDialogController {
    public static final String TAG = GuideDialogController.class.getSimpleName();

    @FXML private VBox step1Pane;
    @FXML private VBox step2Pane;
    @FXML private VBox step3Pane;

    @FXML private ComboBox<String> modelTypeComboBox;

    @FXML private Button backButton;
    @FXML private Button nextButton;

    private List<PlatformType> platformTypeList;

    public String a;

    private int currentStep = 1;

    @FXML
    private void initialize() {
        updateStep();
    }

    @FXML
    private void handleBack(ActionEvent event) {
        if (currentStep > 1) {
            currentStep--;
            updateStep();
        }
    }

    @FXML
    private void handleNext(ActionEvent event) {
        if (currentStep < 3) {
            currentStep++;
            updateStep();
        } else {
            handleFinish(event); // Finish the wizard when on the last step
        }
    }

    @FXML
    private void handleFinish(ActionEvent event) {
        // Handle the finish action here
        LogUtils.i(TAG,modelTypeComboBox.getValue());

//        System.out.println("Finished with selections: " +
//                modelTypeComboBox.getValue() + ", " +
//                modelComboBox.getValue() + ", " +
//                additionalInfoComboBox.getValue());

        // Close the dialog
        Stage stage = (Stage) step1Pane.getScene().getWindow();
        stage.close();
    }

    private void updateStep() {
        System.out.println("Updating step: " + currentStep); // Debugging line
        step1Pane.setVisible(currentStep == 1);
        step2Pane.setVisible(currentStep == 2);
        step3Pane.setVisible(currentStep == 3);

        backButton.setVisible(currentStep > 1);
        nextButton.setVisible(currentStep < 3);

        if (currentStep == 3) {
            nextButton.setText("Finish");
        } else {
            nextButton.setText("->");
        }
    }

}
