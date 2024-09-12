package com.pvetec.inspectra.transmission;

import com.pvetec.inspectra.enums.ProjectType;
import com.pvetec.inspectra.enums.TransmissionType;
import com.pvetec.inspectra.transmission.listener.DeviceConnectionListener;
import com.pvetec.inspectra.transmission.serial.SerialTransmission;
import com.pvetec.inspectra.transmission.socket.SocketTransmission;
import com.pvetec.inspectra.transmission.usb.USBTransmission;

/**
 * Factory class for creating different types of Transmission instances.
 * @author LIWEI
 */
public class TransmissionFactory {

    /**
     * Creates a Transmission instance based on the specified project name.
     *
     * @param projectName the project name
     * @param listener the device connection listener
     * @return the created Transmission instance
     */
    public static Transmission createCommunication(String projectName, DeviceConnectionListener listener) {

        ProjectType projectType = ProjectType.fromName(projectName);

        TransmissionType type = projectType.getCommunicationType();

        return switch (type) {
            case USB -> new USBTransmission(listener);
            case SERIAL -> new SerialTransmission(listener);
            case SOCKET -> new SocketTransmission();
        };
    }
}