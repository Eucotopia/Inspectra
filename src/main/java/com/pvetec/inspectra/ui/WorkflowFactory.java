package com.pvetec.inspectra.ui;

import com.pvetec.inspectra.enums.StationEnum;
import com.pvetec.inspectra.interfaces.StationTestWorkflow;

/**
 * Factory class to create instances of StationTestWorkflow based on the station code.
 * <p>
 * Author: LIWEI
 */
public class WorkflowFactory {

    /**
     * Creates an instance of StationTestWorkflow based on the provided station code.
     *
     * @param station The code representing the station type.
     * @return An instance of StationTestWorkflow corresponding to the station code.
     * @throws IllegalArgumentException if the station code does not match any known type.
     */
    public static StationTestWorkflow createWorkflow(Integer station) {
        if (StationEnum.SN_WRITER.getCode().equals(station)) {
            return new SnWriterTestWorkflow();
        } else if (StationEnum.VERIFICATION_NUMBER.getCode().equals(station)) {
            return new VerificationNumberTestWorkflow();
        } else {
            throw new IllegalArgumentException("Unknown station: " + StationEnum.fromCode(station));
        }
    }
}