package com.pvetec.inspectra.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author LIWEI
 */
public class AboutDialogController {
    @FXML
    private Button closeButton;

    @FXML
    private void handleClose() {
        if (closeButton != null) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }
    }
}
