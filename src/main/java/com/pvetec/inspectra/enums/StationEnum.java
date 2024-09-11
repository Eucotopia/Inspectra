package com.pvetec.inspectra.enums;

import com.pvetec.inspectra.constants.StationConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Enum representing different types of stations in the inspection process.
 * Each station type has a unique code, a name, and a description.
 */
@Getter
@ToString
@AllArgsConstructor
public enum StationEnum {

    /**
     * Station responsible for writing serial numbers.
     */
    SN_WRITER(StationConstant.SN_WRITER, "SN_Writer", "Station for writing serial numbers"),

    /**
     * Station responsible for verifying numbers.
     */
    VERIFICATION_NUMBER(StationConstant.VERIFICATION_NUMBER, "Verification_Number", "Station for verifying numbers");

    /**
     * The unique code for the station.
     */
    private final Integer code;

    /**
     * The name of the station.
     */
    private final String name;

    /**
     * A brief description of the station's function.
     */
    private final String description;
}
