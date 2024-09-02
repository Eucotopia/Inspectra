package com.pvetec.inspectra.transmission.listener;

/**
 * Device connection listener interface for handling device connection and disconnection events.
 */
public interface DeviceConnectionListener {
    /**
     * Called when a device is connected.
     */
    void onDeviceConnected(String serialNumber);

    /**
     * Called when a device is disconnected.
     */
    void onDeviceDisconnected();
}