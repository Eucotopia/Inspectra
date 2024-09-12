package com.pvetec.inspectra.pojo;

import com.pvetec.inspectra.enums.TestStatus;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Data;

@Data
public class TestItemView {
    private final Label nameJLabel;
    private final Circle resultCircle;
    private TestItem testItem;

    public TestItemView(String name) {
        nameJLabel = new Label(name);
        this.resultCircle = new Circle(5.0); // Initialize circle with a default radius
        this.resultCircle.setFill(Color.GRAY); // Default color is red
    }

    // Update the result circle color based on the status
    public void updateStatus(TestStatus status) {
        switch (status) {
            case PASSED:
                resultCircle.setFill(Color.GREEN);
                break;
            case NOT_TESTED:
                resultCircle.setFill(Color.GRAY);
                break;
            case FAILED:
            default:
                resultCircle.setFill(Color.RED); // Default to red if status is unknown
        }
    }
}
