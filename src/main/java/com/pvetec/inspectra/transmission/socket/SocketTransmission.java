package com.pvetec.inspectra.transmission.socket;

import com.pvetec.inspectra.transmission.Transmission;

public class SocketTransmission implements Transmission {
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
