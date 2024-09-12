package com.pvetec.inspectra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the configuration parameters for a serial port connection.
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class SerialPortConfig {
    private String serialPortName; // Name of the serial port (e.g., "COM12")
    private int baudRate;          // Baud rate for the serial communication
    private int dataBits;          // Number of data bits (typically 7 or 8)
    private int stopBits;          // Number of stop bits (typically 1 or 2)
    private int parity;            // Parity setting (e.g., 0 for NO_PARITY)
}
