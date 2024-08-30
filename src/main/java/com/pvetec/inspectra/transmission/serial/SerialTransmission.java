package com.pvetec.inspectra.transmission.serial;

import com.pvetec.inspectra.transmission.Transmission;
import com.pvetec.inspectra.transmission.listener.DeviceConnectionListener;

public class SerialTransmission implements Transmission {

    public SerialTransmission(DeviceConnectionListener connectionListener){

    }
    @Override
    public void open() throws Exception {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void send(byte[] data) throws Exception {

    }

    @Override
    public byte[] receive() throws Exception {
        return new byte[0];
    }

    @Override
    public boolean isDeviceConnected() {
        return false;
    }
}
