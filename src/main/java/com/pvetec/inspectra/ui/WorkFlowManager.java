package com.pvetec.inspectra.ui;

import com.pvetec.inspectra.enums.StationEnum;
import com.pvetec.inspectra.interfaces.StationTestWorkflow;
import javafx.scene.layout.VBox;

/**
 * @author LIWEI
 */
public class WorkFlowManager {
    private StationTestWorkflow stationTestWorkflow;

    public void setStationTestWorkflow(StationEnum stationEnum) {
        this.stationTestWorkflow = WorkflowFactory.createWorkflow(stationEnum);
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
