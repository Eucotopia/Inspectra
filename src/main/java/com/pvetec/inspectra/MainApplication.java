package com.pvetec.inspectra;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Hello!");
        // 调用方法设置窗口大小和位置
        setWindowSizeAndPosition(stage);

        stage.setScene(scene);
        stage.show();
    }

    private void setWindowSizeAndPosition(Stage stage) {
        // 获取屏幕的尺寸
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // 设置窗口的大小为屏幕的一半
        double windowWidth = screenWidth / 2;
        double windowHeight = screenHeight / 2;

        // 设置窗口的大小
        stage.setWidth(windowWidth);
        stage.setHeight(windowHeight);

        // 计算窗口的居中位置
        double windowX = (screenWidth - windowWidth) / 2;
        double windowY = (screenHeight - windowHeight) / 2;

        // 设置窗口的位置
        stage.setX(windowX);
        stage.setY(windowY);
    }


    public static void main(String[] args) {
        launch();
    }
}