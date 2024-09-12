package com.pvetec.inspectra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the configuration parameters for a serial port connection.
 * This class contains settings for establishing and managing a serial port connection.
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class SerialPortConfig {
    /**
     * Name of the serial port (e.g., "COM12").
     * This identifies which serial port is being used for communication.
     */
    private String serialPortName;

    /**
     * Baud rate for the serial communication.
     * This defines the speed of data transmission, typically expressed in bits per second (bps).
     */
    private int baudRate;

    /**
     * Number of data bits.
     * This specifies the number of bits used in each data unit (typically 7 or 8 bits).
     */
    private int dataBits;

    /**
     * Number of stop bits.
     * This defines the number of bits used to signal the end of a data unit (typically 1 or 2 bits).
     */
    private int stopBits;

    /**
     * Parity setting.
     * This is used for error checking during data transmission, with common values such as 0 for NO_PARITY.
     */
    private int parity;
}