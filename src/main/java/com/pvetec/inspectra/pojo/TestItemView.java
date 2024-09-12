package com.pvetec.inspectra.pojo;

import com.pvetec.inspectra.enums.TestStatus;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Data;

/**
 * @author LIWEI
 */
@Data
public class TestItemView {
    private final Label nameJLabel;
    private final Circle resultCircle;
    private TestItem testItem;

    public TestItemView(String name) {
        nameJLabel = new Label(name);
        // Initialize circle with a default radius
        this.resultCircle = new Circle(5.0);
        // Default color is red
        this.resultCircle.setFill(Color.GRAY);
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
                // Default to red if status is unknown
                resultCircle.setFill(Color.RED);
        }
    }
}
