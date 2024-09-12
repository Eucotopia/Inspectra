package com.pvetec.inspectra.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author LIWEI
 */

@Getter
@ToString
@AllArgsConstructor
public enum VerificationNumber {
    BLUETOOTH_ADDRESS("Bluetooth Address", 1),
    WIFI_ADDRESS("WiFi Address", 2),
    SERIAL_NUMBER("Serial Number", 4),
    DEVICE_ID("Device ID", 5),
    IMEI_1("IMEI_1", 6),
    IMEI_2("IMEI_2", 7);

    private final String description;
    private final int code;
}
