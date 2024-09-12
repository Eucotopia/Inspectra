package com.pvetec.inspectra;

import com.pvetec.inspectra.controller.NavigationBarController;
import com.pvetec.inspectra.controller.TestAreaController;
import com.pvetec.inspectra.pojo.SharedData;
import com.pvetec.inspectra.utils.LogUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.io.IOException;

public class MainController {

    public static final String TAG = MainController.class.getSimpleName();

    public static final SharedData sharedData = new SharedData();

    @FXML
    private VBox testAreaVBox;

    @FXML
    private VBox navigationBarVBox;

    @Getter
    private TestAreaController testAreaController;

    @Getter
    private NavigationBarController navigationBarController;

    @FXML
    public void initialize() {
        loadTestArea();
        loadNavigationBar();
        System.out.println("initialize:" + sharedData);
        navigationBarController.setSharedData(sharedData);
        testAreaController.setSharedData(sharedData);
    }

    private void loadTestArea() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TestArea.fxml"));
            HBox testArea = loader.load();
            testAreaController = loader.getController();
            testAreaVBox.getChildren().add(testArea);

        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage());
        }
    }

    private void loadNavigationBar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NavigationBar.fxml"));
            HBox navigationBar = loader.load();
            navigationBarController = loader.getController();
            navigationBarVBox.getChildren().add(navigationBar);
        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage());
        }
    }

}
