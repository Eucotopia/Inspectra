package com.pvetec.inspectra.transmission.usb;

import cn.hutool.core.bean.BeanUtil;
import com.pvetec.inspectra.transmission.Transmission;
import com.pvetec.inspectra.transmission.listener.DeviceConnectionListener;
import com.pvetec.inspectra.utils.ADBUtil;
import com.pvetec.inspectra.utils.LogUtils;


import javax.usb.*;
import javax.usb.event.UsbServicesEvent;
import javax.usb.event.UsbServicesListener;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class USBTransmission implements Transmission {

    public static final String TAG = USBTransmission.class.getSimpleName();

    private static final short MTK_VENDOR_ID = 0x0e8d;

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
        // Implement USB open logic
    }

    @Override
    public void close() throws Exception {
        // Implement USB close logic
    }

    @Override
    public void send(byte[] data) throws Exception {
        // Implement USB send logic
    }

    @Override
    public byte[] receive() throws Exception {
        // Implement USB receive logic
        return new byte[0];
    }

    @Override
    public boolean isDeviceConnected() {
        // Implement logic to check if a device is connected
        return false;
    }

    private final UsbServicesListener usbServicesListener = new UsbServicesListener() {
        @Override
        public void usbDeviceAttached(UsbServicesEvent event) {
            UsbDevice device = event.getUsbDevice();
            try {
                if (isAndroidDevice(device)) {
                    LogUtils.highlight(TAG, "usbDeviceAttached usbServicesEvent: " + device);
                    LogUtils.highlight(TAG, "usbDeviceAttached serialNumber: " + ADBUtil.getFirstAdbDevice());
                    if (connectionListener != null) {
                        connectionListener.onDeviceConnected(ADBUtil.getFirstAdbDevice());
                    }
                } else {
                    LogUtils.i(TAG, "Connected USB device is not an Android device");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void usbDeviceDetached(UsbServicesEvent event) {
            UsbDevice device = event.getUsbDevice();
            if (isAndroidDevice(device)) {
                LogUtils.w(TAG, "usbDeviceDetached usbServicesEvent : " + device);
                if (connectionListener != null) {
                    connectionListener.onDeviceDisconnected();
                }
            }
        }
    };

    /**
     * Checks if the given USB device is an Android device.
     *
     * @param device The USB device to check.
     * @return true if the device is an Android device, false otherwise.
     */
    private boolean isAndroidDevice(UsbDevice device) {
        UsbDeviceDescriptor descriptor = device.getUsbDeviceDescriptor();
        return descriptor.idVendor() == MTK_VENDOR_ID;
    }
}
