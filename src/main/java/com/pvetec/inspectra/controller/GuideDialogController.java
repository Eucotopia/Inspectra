package com.pvetec.inspectra.controller;

import com.pvetec.inspectra.MainController;
import com.pvetec.inspectra.pojo.SharedData;
import com.pvetec.inspectra.pojo.CurrentTest;
import com.pvetec.inspectra.pojo.Platform;
import com.pvetec.inspectra.pojo.Project;
import com.pvetec.inspectra.pojo.TestType;
import com.pvetec.inspectra.utils.JsonBeanConverter;
import com.pvetec.inspectra.utils.LogUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GuideDialogController {

    public static final String TAG = GuideDialogController.class.getSimpleName();

    private SharedData sharedData;

    public void setSharedData(SharedData sharedData) {
        this.sharedData = sharedData;
        System.out.println("setSharedData:" + this.sharedData);
    }


    private List<Platform> platformList;

    private Platform selectedPlatform;

    private Project selectedProject;

    private int currentStep = 1;

    @FXML
    private VBox step1Pane;
    @FXML
    private VBox step2Pane;
    @FXML
    private VBox step3Pane;

    @FXML
    private ComboBox<String> platformComboBox;
    @FXML
    private ComboBox<String> projectTypeComboBox;
    @FXML
    private ComboBox<String> testTypeComboBox;

    @FXML
    private Button backButton;
    @FXML
    private Button nextButton;

    private String test;

    @FXML
    private void initialize() {
        test = "adsfasdf";
        if (platformList != null) {
            initializeComboBoxes();
        }
        updateStep();
    }

    @FXML
    private void handleBack() {
        if (currentStep > 1) {
            currentStep--;
            updateStep();
        }
    }

    @FXML
    private void handleNext() {
        if (currentStep < 3) {
            currentStep++;
            updateStep();
        } else {
            handleFinish(); // Finish the wizard when on the last step
        }
    }

    @FXML
    private void handleFinish() {
        String selectedPlatformName = platformComboBox.getValue();
        String selectedProjectName = projectTypeComboBox.getValue();
        String selectedTestTypeName = testTypeComboBox.getValue();

        if (selectedPlatformName == null || selectedProjectName == null || selectedTestTypeName == null) {
            LogUtil.e(TAG, "Please make sure all selections are made.");
            return;
        }

        selectedPlatform = platformList.stream()
                .filter(platform -> platform.getName().equals(selectedPlatformName))
                .findFirst()
                .orElse(null);

        if (selectedPlatform == null) {
            LogUtil.e(TAG, "Selected platform not found.");
            return;
        }

        selectedProject = selectedPlatform.getProjects().stream()
                .filter(project -> project.getName().equals(selectedProjectName))
                .findFirst()
                .orElse(null);

        if (selectedProject == null) {
            LogUtil.e(TAG, "Selected project not found.");
            return;
        }

        TestType selectedTestType = selectedProject.getTestTypes().stream()
                .filter(testType -> testType.getName().equals(selectedTestTypeName))
                .findFirst()
                .orElse(null);

        if (selectedTestType == null) {
            LogUtil.e(TAG, "Selected test type not found.");
            return;
        }

        // Create TestTypeData object
        TestType testTypeData = new TestType();
        testTypeData.setName(selectedTestType.getName());
        testTypeData.setCode(selectedTestType.getCode());
        testTypeData.setItems(selectedTestType.getItems());

        // Create final JSON structure
        CurrentTest finishData = new CurrentTest();
//        finishData.setName(selectedProject.getName());
        finishData.setTestType(testTypeData);

        // Save data to file using JsonBeanConverter
        try {
            JsonBeanConverter.beanToFile(finishData, "config/current_test.json");
        } catch (IOException e) {
            LogUtil.e(TAG, "Failed to save data to file:" + e.getMessage());
        }

        if (sharedData != null) {
            sharedData.setOperationFinished(!sharedData.operationFinishedProperty().get());
        }

        // Close the dialog
        Stage stage = (Stage) step1Pane.getScene().getWindow();
        stage.close();
    }

    private void updateStep() {
        step1Pane.setVisible(currentStep == 1);
        step2Pane.setVisible(currentStep == 2);
        step3Pane.setVisible(currentStep == 3);

        backButton.setVisible(currentStep > 1);
        nextButton.setVisible(currentStep < 3 || currentStep == 3); // Ensure it shows up at step 3

        nextButton.setText(currentStep == 3 ? "Finish" : "->");

    }

    public void setPlatformList(List<Platform> platformList) {
        this.platformList = platformList;
        if (platformComboBox != null) {  // If ComboBox has been initialized
            initializeComboBoxes();
        }
    }

    private void initializeComboBoxes() {
        // 平台类型
        platformComboBox.getItems().clear();
        // 项目类型
        projectTypeComboBox.getItems().clear();
        // 测试类型
        testTypeComboBox.getItems().clear();

        platformList.forEach(platform -> platformComboBox.getItems().add(platform.getName()));

        if (!platformComboBox.getItems().isEmpty()) {
            platformComboBox.getSelectionModel().select(0);
            updateProjectTypeComboBox();
        }

        platformComboBox.setOnAction(event -> updateProjectTypeComboBox());

        projectTypeComboBox.setOnAction(event -> updateTestTypeComboBox());
    }

    @SafeVarargs
    private void clearComboBoxes(ComboBox<String>... comboBoxes) {
        for (ComboBox<String> comboBox : comboBoxes) {
            comboBox.getItems().clear();
        }
    }

    private void updateProjectTypeComboBox() {
        String selectedPlatformName = platformComboBox.getValue();
        selectedPlatform = platformList.stream()
                .filter(platform -> platform.getName().equals(selectedPlatformName))
                .findFirst()
                .orElse(null);

        clearComboBoxes(projectTypeComboBox, testTypeComboBox);

        if (selectedPlatform != null) {
            selectedPlatform.getProjects().forEach(project -> projectTypeComboBox.getItems().add(project.getName()));

            if (!projectTypeComboBox.getItems().isEmpty()) {
                projectTypeComboBox.getSelectionModel().select(0);
                updateTestTypeComboBox();
            }
        }
    }

    private void updateTestTypeComboBox() {
        String selectedProjectName = projectTypeComboBox.getValue();
        if (selectedPlatform != null) {
            selectedProject = selectedPlatform.getProjects().stream()
                    .filter(project -> project.getName().equals(selectedProjectName))
                    .findFirst()
                    .orElse(null);

            clearComboBoxes(testTypeComboBox);

            if (selectedProject != null) {
                selectedProject.getTestTypes().forEach(testType -> testTypeComboBox.getItems().add(testType.getName()));

                if (!testTypeComboBox.getItems().isEmpty()) {
                    testTypeComboBox.getSelectionModel().select(0);
                }
            }
        }
    }
}