package com.pvetec.inspectra.controller;

import com.pvetec.inspectra.pojo.SharedData;
import com.pvetec.inspectra.pojo.TestItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller class for handling the test area.
 */
public class TestAreaController {
    public static final String TAG = TestAreaController.class.getSimpleName();

    @FXML
    private Label welcomeText;

    @FXML
    private TableView<TestItem> testTableView;

    @FXML
    private TableColumn<TestItem, Integer> codeColumn;

    @FXML
    private TableColumn<TestItem, String> nameColumn;

    @FXML
    private TableColumn<TestItem, String> resultColumn;

    public void setSharedData(SharedData sharedData) {
        // 监听设备连接状态和操作完成状态的变化
        sharedData.deviceConnectedProperty().addListener((observable, oldValue, newValue) -> {
            // 检查状态是否从 disconnected (false) 变为 connected (true)
            if (oldValue != newValue) {
                if (newValue) {
                    welcomeText.setText("Device Connected. Starting Test...");
                } else {
                    welcomeText.setText("Device Disconnected. Stopping Test...");
                }
            }
        });


        sharedData.operationFinishedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                System.out.println("asdfasdfasfadsf");
            }
        });
    }

    @FXML
    private void initialize() {
        // 设置列的CellValueFactory
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));

        // 手动添加一些测试项
        TestItem item1 = new TestItem(1, "Test Item 1", "Pending");
        TestItem item2 = new TestItem(2, "Test Item 2", "Pending");
        TestItem item3 = new TestItem(3, "Test Item 3", "Pending");
        TestItem item4 = new TestItem(4, "Test Item 4", "Pending");

        testTableView.getItems().addAll(item1, item2, item3, item4);
    }
}
