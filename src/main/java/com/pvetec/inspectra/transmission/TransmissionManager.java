package com.pvetec.inspectra.transmission;

import com.pvetec.inspectra.transmission.listener.DeviceConnectionListener;

/**
 * Manager class for handling different types of Transmission instances.
 * @author LIWEI
 */
public class TransmissionManager {

    private Transmission transmission;

    /**
     * Checks if the device is connected.
     *
     * @return true if the device is connected, false otherwise
     */
    public boolean isDeviceConnected() {
        if (transmission != null) {
            return transmission.isDeviceConnected();
        }
        return false;
    }

    /**
     * Sets the communication type and initializes the Transmission instance.
     *
     * @param project the type of transmission ("USB", "Serial", "Socket")
     * @param listener the device connection listener
     */
    public void setCommunication(String project, DeviceConnectionListener listener) {
        this.transmission = TransmissionFactory.createCommunication(project, listener);
    }

    /**
     * Opens the connection.
     *
     * @throws Exception if an error occurs while opening the connection
     */
    public void openConnection() throws Exception {
        if (transmission != null) {
            transmission.open();
        }
    }

    /**
     * Closes the connection.
     *
     * @throws Exception if an error occurs while closing the connection
     */
    public void closeConnection() throws Exception {
        if (transmission != null) {
            transmission.close();
        }
    }

    /**
     * Sends data.
     *
     * @param data the data to send
     * @throws Exception if an error occurs while sending data
     */
    public void sendData(byte[] data) throws Exception {
        if (transmission != null) {
            transmission.send(data);
        }
    }

    /**
     * Receives data.
     *
     * @return the received data
     * @throws Exception if an error occurs while receiving data
     */
    public byte[] receiveData() throws Exception {
        if (transmission != null) {
            return transmission.receive();
        }
        return new byte[0];
    }
}