package com.pvetec.inspectra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationValues {

    // Bluetooth address prefix validation
    private String bluetoothAddrPrefix;

    // Wi-Fi address prefix validation
    private String wifiAddrPrefix;

    // Serial number length validation
    private Integer serialNumberLength;

    // IMEI validation
    private String imei1;

    private String imei2;
}