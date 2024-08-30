package com.pvetec.inspectra.transmission;

public interface Transmission {
    void open() throws Exception;
    void close() throws Exception;
    void send(byte[] data) throws Exception;
    byte[] receive() throws Exception;
    boolean isDeviceConnected();
}
