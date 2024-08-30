package com.pvetec.inspectra.transmission.usb;

import com.pvetec.inspectra.transmission.Transmission;
import com.pvetec.inspectra.transmission.listener.DeviceConnectionListener;
import com.pvetec.inspectra.utils.LogUtils;


import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbServices;
import javax.usb.event.UsbServicesEvent;
import javax.usb.event.UsbServicesListener;

public class USBTransmission implements Transmission {

    public static final String TAG = USBTransmission.class.getSimpleName();

    private final DeviceConnectionListener connectionListener;


    public USBTransmission(DeviceConnectionListener connectionListener) {

        this.connectionListener = connectionListener;

        try {
            UsbServices usbServices = UsbHostManager.getUsbServices();
            usbServices.addUsbServicesListener(usbServicesListener);
        } catch (UsbException e) {
            e.printStackTrace();
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


    private final UsbServicesListener usbServicesListener = new UsbServicesListener() {

        @Override
        public void usbDeviceAttached(UsbServicesEvent event) {
            LogUtils.i(TAG,"USB device connected");
            if (connectionListener != null) {
                connectionListener.onDeviceConnected();
            }
        }

        @Override
        public void usbDeviceDetached(UsbServicesEvent event) {
            LogUtils.e(TAG,"USB device disconnected");
            LogUtils.highlight(TAG,"USB device detached");
            if (connectionListener != null) {
                connectionListener.onDeviceDisconnected();
            }
        }
    };
}
