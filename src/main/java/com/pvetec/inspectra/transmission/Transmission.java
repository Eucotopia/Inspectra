package com.pvetec.inspectra.transmission;

/**
 * Interface representing a communication transmission.
 * It includes methods to open and close the transmission, send and receive data,
 * and check if the device is connected.
 */
public interface Transmission {

    /**
     * Opens the transmission channel.
     *
     * @throws Exception if an error occurs while opening the transmission.
     */
    void open() throws Exception;

    /**
     * Closes the transmission channel.
     *
     * @throws Exception if an error occurs while closing the transmission.
     */
    void close() throws Exception;

    /**
     * Sends data over the transmission channel.
     *
     * @param data The data to be sent.
     * @throws Exception if an error occurs while sending the data.
     */
    void send(byte[] data) throws Exception;

    /**
     * Receives data from the transmission channel.
     *
     * @return The received data as a byte array.
     * @throws Exception if an error occurs while receiving the data.
     */
    byte[] receive() throws Exception;

    /**
     * Checks if the device is connected.
     *
     * @return true if the device is connected, false otherwise.
     */
    boolean isDeviceConnected();
}
