package com.pvetec.inspectra.transmission;

import com.pvetec.inspectra.transmission.serial.SerialTransmission;
import com.pvetec.inspectra.transmission.socket.SocketTransmission;
import com.pvetec.inspectra.transmission.usb.USBTransmission;
import com.pvetec.inspectra.transmission.listener.DeviceConnectionListener;

/**
 * Factory class for creating different types of Transmission instances.
 */
public class TransmissionFactory {
    /**
     * Creates a Transmission instance based on the specified type.
     *
     * @param type the type of transmission ("USB", "Serial", "Socket")
     * @param listener the device connection listener
     * @return the created Transmission instance
     */
    public static Transmission createCommunication(String type, DeviceConnectionListener listener) {
        return switch (type) {
            case "USB" -> new USBTransmission(listener);
            case "Serial" -> new SerialTransmission(listener);
            case "Socket" -> new SocketTransmission();
            default -> throw new IllegalArgumentException("Unknown communication type: " + type);
        };
    }
}