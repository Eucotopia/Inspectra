package com.pvetec.inspectra.ui;

import com.pvetec.inspectra.enums.StationEnum;
import com.pvetec.inspectra.interfaces.StationTestWorkflow;
import com.pvetec.inspectra.pojo.TestItemView;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LIWEI
 */
public class WorkFlowManager {

    private StationTestWorkflow stationTestWorkflow;

    public void setStationTestWorkflow(Integer station) {
        this.stationTestWorkflow = WorkflowFactory.createWorkflow(station);
    }

    /**
     * 根据站点枚举初始化测试界面
     *
     * @param vbox         用于显示测试表单的 VBox
     */
    public void initializeTestArea(VBox vbox) {

        // 如果工作流实例为 null，则返回
        if (stationTestWorkflow == null) {
            throw new IllegalArgumentException("Unknown station ");
        }

        // 创建测试表单
        stationTestWorkflow.createTestArea(vbox);
    }

    public void startTest() {
        stationTestWorkflow.startTest();
    }

    public void resetTest() {
        stationTestWorkflow.resetTest();
    }
}
