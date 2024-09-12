package com.pvetec.inspectra.interfaces;

import javafx.scene.layout.VBox;

/**
 * @author LIWEI
 */
public interface StationTestWorkflow {
    /**
     * Starts the test process.
     * This method should contain the logic to initiate the test.
     */
    void startTest();

    /**
     * Creates and initializes the test area within the given VBox.
     * This method allows the setup of a user interface component (VBox)
     * that will be used for the test.
     *
     * @param vbox The VBox component where the test area should be created.
     */
    void createTestArea(VBox vbox);

    /**
     * Resets the test environment to its initial state.
     * This method should clear any test results and prepare the system
     * for a new test.
     */
    void resetTest();
}
