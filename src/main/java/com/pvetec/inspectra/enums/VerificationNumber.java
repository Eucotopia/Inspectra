package com.pvetec.inspectra.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum VerificationNumber {
    BLUETOOTH_ADDRESS("Bluetooth Address", 1),
    WIFI_ADDRESS("WiFi Address", 2),
    MAC_ADDRESS("MAC Address", 3),
    SERIAL_NUMBER("Serial Number", 4),
    DEVICE_ID("Device ID", 5),
    IMEI("IMEI", 6);

    private final String description;
    private final int code;
}
