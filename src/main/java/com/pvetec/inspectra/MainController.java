package com.pvetec.inspectra;

import com.pvetec.inspectra.controller.GuideDialogController;
import com.pvetec.inspectra.controller.NavigationBarController;
import com.pvetec.inspectra.controller.TestAreaController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import jdk.incubator.vector.VectorOperators;

import java.io.IOException;

public class MainController {


    @FXML
    private NavigationBarController navigationBarController;

    @FXML
    private TestAreaController testAreaController;

    @FXML
    private GuideDialogController guideDialogController;

    @FXML
    private void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pvetec/inspectra/controller/DialogView.fxml"));
            Parent guideDialogRoot = loader.load();
            guideDialogController = loader.getController();
            guideDialogController.setConfigUpdateListener(testAreaController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}