package com.pvetec.inspectra.ui;

import com.pvetec.inspectra.enums.StationEnum;
import com.pvetec.inspectra.interfaces.StationTestWorkflow;

/**
 * @author LIWEI
 */
public class WorkflowFactory {
    public static StationTestWorkflow createWorkflow(StationEnum station) {
        return switch (station) {
            case SN_WRITER -> new SnWriterTestWorkflow();
            case VERIFICATION_NUMBER -> new VerificationNumberTestWorkflow();
            default -> throw new IllegalArgumentException("Unknown station: " + station);
        };
    }
}
