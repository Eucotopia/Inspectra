package com.pvetec.inspectra.ui;

import com.pvetec.inspectra.constants.FilePathConstant;
import com.pvetec.inspectra.enums.TestStatus;
import com.pvetec.inspectra.interfaces.StationTestWorkflow;
import com.pvetec.inspectra.pojo.CurrentTest;
import com.pvetec.inspectra.pojo.TestItem;
import com.pvetec.inspectra.pojo.TestItemView;
import com.pvetec.inspectra.utils.ADBUtil;
import com.pvetec.inspectra.utils.JsonBeanConverter;
import com.pvetec.inspectra.utils.LogUtil;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LIWEI
 */
public class VerificationNumberTestWorkflow implements StationTestWorkflow {

    public static final String TAG = VerificationNumberTestWorkflow.class.getSimpleName();

    private final Map<Integer, TestItemView> testItemViews = new HashMap<>();

    @Override
    public void startTest() {
        LogUtil.e(TAG, "Starting Verification Number test...");

        if (testItemViews.isEmpty()) {
            LogUtil.e(TAG, "No test items available.");
            return;
        }
        for (TestItemView value : testItemViews.values()) {
            switch (value.getTestItem().getCode()) {
                case 1:
                    // 蓝牙地址测试
                    value.updateStatus(TestStatus.PASSED);
                case 2:
                    // Wi-Fi 地址测试
                    value.updateStatus(TestStatus.FAILED);
                default:
                    break;
            }
        }
        // Get the first item (you might want to handle cases where there are no items)
        TestItemView firstItemView = testItemViews.values().iterator().next();
        TestItem firstItem = firstItemView.getTestItem();

        // Perform the test and update the result
        // Replace with actual result logic
        String result = readNumberFromDevice();

        // Update the result of the first test item
        firstItem.setResult(result);

        // Update the result circle and label for the first test item in the UI
        firstItemView.updateStatus(determineStatus(result));

    }

    @Override
    public void createTestArea(VBox vbox) {
        // Clear existing content in the VBox
        vbox.getChildren().clear();

        try {
            CurrentTest currentTest = JsonBeanConverter.fileToBean(FilePathConstant.CURRENT_TEST, CurrentTest.class);
            for (TestItem item : currentTest.getTestType().getItems()) {
                TestItemView testItemView = new TestItemView(item.getName());
                testItemView.setTestItem(item);
                testItemViews.put(item.getCode(), testItemView);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load current test configuration.", e);
        }

        // Create and add form components
        createVerificationNumberForm(vbox);
    }

    @Override
    public void resetTest() {
        LogUtil.e(TAG, "Resetting test...");

        // Clear the test data
//        testItemViews.clear();
        for (TestItemView value : testItemViews.values()) {
            value.updateStatus(TestStatus.FAILED);
        }
        LogUtil.e(TAG, "Test has been reset.");
    }

    private void createVerificationNumberForm(VBox vbox) {
        // Create a form layout
        VBox formVBox = new VBox(10.0);
        formVBox.setPadding(new javafx.geometry.Insets(10));

        // Create and add Verification Number input
        HBox verificationNumberHBox = new HBox(10.0);
        Label verificationNumberLabel = new Label("Verification Number:");
        TextField verificationNumberField = new TextField();
        verificationNumberField.setPromptText("Enter Verification Number");
        verificationNumberHBox.getChildren().addAll(verificationNumberLabel, verificationNumberField);

        // Create and add Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> handleSubmit(verificationNumberField.getText()));

        // Create and add Start Test Button
        Button startTestButton = new Button("Start Test");
        startTestButton.setOnAction(event -> startTest());

        // Add all components to the form VBox
        formVBox.getChildren().addAll(verificationNumberHBox, submitButton, startTestButton);

        // Add test items details to the form VBox
        for (TestItemView testItemView : testItemViews.values()) {
            TestItem item = testItemView.getTestItem();
            HBox itemHBox = new HBox(10.0);
            Label descriptionLabel = new Label("Description: " + item.getDescription());
            Label resultLabel = new Label("Result: " + (item.getResult() != null ? item.getResult() : "N/A"));
            Label statusLabel = new Label("Status:");
            statusLabel.setGraphic(testItemView.getResultCircle());

            itemHBox.getChildren().addAll(testItemView.getNameJLabel(), descriptionLabel, resultLabel, statusLabel);
            formVBox.getChildren().add(itemHBox);
        }

        // Add form VBox to the main VBox
        vbox.getChildren().add(formVBox);
    }

    private void handleSubmit(String verificationNumber) {
        // Handle the form submission
        LogUtil.e(TAG, "Verification Number: " + verificationNumber);

        // Implement logic to read the actual number from the device using ADB
    }

    private TestStatus determineStatus(String result) {
        // Implement logic to determine the status based on result
        // Example logic (replace with actual implementation)
        return result.equals("123456") ? TestStatus.PASSED : TestStatus.FAILED;
    }

    private String readNumberFromDevice() {
        // Stub for reading from device
        // Example value
        return "123456";
    }
}
