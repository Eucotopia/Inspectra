package com.pvetec.inspectra.transmission.serial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.pvetec.inspectra.transmission.Transmission;
import com.pvetec.inspectra.transmission.listener.DeviceConnectionListener;
import com.pvetec.inspectra.utils.LogUtil;


public class SerialTransmission implements Transmission {
    final static String TAG = SerialTransmission.class.getSimpleName();
    private final DeviceConnectionListener connectionListener;

    public SerialTransmission(DeviceConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
        try {
            SerialPort[] ports = SerialPort.getCommPorts();
            SerialPort port = ports[0];
            port.openPort();
            port.setComPortParameters(115200,8,1,SerialPort.NO_PARITY);
            port.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return 0;
                }

                @Override
                public void serialEvent(SerialPortEvent serialPortEvent) {
                    switch (serialPortEvent.getEventType()) {
                        case SerialPort.LISTENING_EVENT_DATA_AVAILABLE:
                            break;
                        case SerialPort.LISTENING_EVENT_BREAK_INTERRUPT:
                            break;
                        case SerialPort.LISTENING_EVENT_PORT_DISCONNECTED:
                            connectionListener.onDeviceDisconnected();
                            LogUtil.w(TAG,"disconnected");
                            break;
                        default:
                            break;
                    }
                }
            });
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
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
