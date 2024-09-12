package com.pvetec.inspectra.transmission.usb;

import com.pvetec.inspectra.transmission.Transmission;
import com.pvetec.inspectra.transmission.listener.DeviceConnectionListener;
import com.pvetec.inspectra.utils.ADBUtil;
import com.pvetec.inspectra.utils.LogUtil;

import javax.usb.*;
import javax.usb.event.UsbServicesEvent;
import javax.usb.event.UsbServicesListener;
import java.util.List;

public class USBTransmission implements Transmission {

    public static final String TAG = USBTransmission.class.getSimpleName();
    private static final short MTK_VENDOR_ID = 0x0e8d; // Vendor ID for MTK devices

    private final DeviceConnectionListener connectionListener;
    private UsbDevice usbDevice;
    private UsbInterface usbInterface;
    private UsbEndpoint endpointIn;
    private UsbEndpoint endpointOut;

    public USBTransmission(DeviceConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
    }

    @Override
    public void open() throws Exception {
        try {
            UsbServices usbServices = UsbHostManager.getUsbServices();
            usbServices.addUsbServicesListener(usbServicesListener);

            // Try to find and initialize the Android device if already attached
            UsbDevice existingDevice = findAndroidDevice();
            if (existingDevice != null) {
                initializeDevice(existingDevice);
            } else {
                LogUtil.i(TAG, "No Android device found initially.");
            }
        } catch (UsbException e) {
            LogUtil.e(TAG, "Failed to open USB device: " + e.getMessage());
            throw new Exception("Failed to open USB device.", e);
        }
    }

    @Override
    public void close() throws Exception {
        try {
            if (usbInterface != null) {
                usbInterface.release();
            }
            if (usbDevice != null) {
                UsbHostManager.getUsbServices().removeUsbServicesListener(usbServicesListener);
            }
            LogUtil.i(TAG, "USB device closed successfully.");
        } catch (UsbException e) {
            LogUtil.e(TAG, "Failed to close USB device: " + e.getMessage());
            throw new Exception("Failed to close USB device.", e);
        }
    }

    @Override
    public void send(byte[] data) throws Exception {
        try {
            if (endpointOut == null) {
                throw new Exception("OUT endpoint is not set.");
            }
            UsbPipe pipe = endpointOut.getUsbPipe();
            pipe.open();
            pipe.syncSubmit(data);
            pipe.close();
            LogUtil.i(TAG, "Data sent successfully.");
        } catch (UsbException e) {
            LogUtil.e(TAG, "Failed to send data: " + e.getMessage());
            throw new Exception("Failed to send data.", e);
        }
    }

    @Override
    public byte[] receive() throws Exception {
        try {
            if (endpointIn == null) {
                throw new Exception("IN endpoint is not set.");
            }
            UsbPipe pipe = endpointIn.getUsbPipe();
            pipe.open();
            byte[] data = new byte[endpointIn.getUsbEndpointDescriptor().wMaxPacketSize()];
            int bytesRead = pipe.syncSubmit(data);
            pipe.close();
            LogUtil.i(TAG, "Data received: " + bytesRead + " bytes.");
            return data;
        } catch (UsbException e) {
            LogUtil.e(TAG, "Failed to receive data: " + e.getMessage());
            throw new Exception("Failed to receive data.", e);
        }
    }

    @Override
    public boolean isDeviceConnected() {
        try {
            UsbServices usbServices = UsbHostManager.getUsbServices();
            UsbHub rootHub = usbServices.getRootUsbHub();
            List<UsbDevice> devices = rootHub.getAttachedUsbDevices(); // Ensure List is of type UsbDevice

            for (UsbDevice device : devices) {
                if (device.equals(this.usbDevice)) {
                    return true;
                }
            }
        } catch (UsbException e) {
            LogUtil.e(TAG, "Error checking device connection: " + e.getMessage());
        }
        return false;
    }

    private UsbDevice findAndroidDevice() {
        try {
            UsbServices usbServices = UsbHostManager.getUsbServices();
            UsbHub rootHub = usbServices.getRootUsbHub();
            List<UsbDevice> devices = rootHub.getAttachedUsbDevices(); // Ensure List is of type UsbDevice
            for (UsbDevice device : devices) {
                if (isAndroidDevice(device)) {
                    return device;
                }
            }
        } catch (UsbException e) {
            LogUtil.e(TAG, "Error finding Android device: " + e.getMessage());
        }
        return null;
    }

    private void initializeDevice(UsbDevice device) throws UsbException {
        this.usbDevice = device;
        UsbConfiguration config = device.getActiveUsbConfiguration();
        this.usbInterface = config.getUsbInterface((byte) 0);

        // Claim the interface
        usbInterface.claim();

        // Set up the endpoints for communication
        this.endpointIn = usbInterface.getUsbEndpoint((byte) 0x81); // IN endpoint
        this.endpointOut = usbInterface.getUsbEndpoint((byte) 0x01); // OUT endpoint

        LogUtil.i(TAG, "USB device initialized successfully.");
    }

    private final UsbServicesListener usbServicesListener = new UsbServicesListener() {
        @Override
        public void usbDeviceAttached(UsbServicesEvent event) {
            UsbDevice device = event.getUsbDevice();
            try {
                if (isAndroidDevice(device)) {
                    LogUtil.i(TAG, "USB device attached: " + device);
                    if (connectionListener != null) {
                        connectionListener.onDeviceConnected(ADBUtil.getFirstAdbDevice());
                    }
                    initializeDevice(device); // Initialize device when attached
                } else {
                    LogUtil.i(TAG, "Connected USB device is not an Android device.");
                }
            } catch (Exception e) {
                LogUtil.e(TAG, "Error handling USB device attachment: " + e.getMessage());
            }
        }

        @Override
        public void usbDeviceDetached(UsbServicesEvent event) {
            UsbDevice device = event.getUsbDevice();
            if (isAndroidDevice(device)) {
                LogUtil.i(TAG, "USB device detached: " + device);
                if (connectionListener != null) {
                    connectionListener.onDeviceDisconnected();
                }
                // Handle cleanup if needed
                cleanupDevice();
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

    private void cleanupDevice() {
        try {
            if (usbInterface != null) {
                try {
                    usbInterface.release();
                } catch (UsbDisconnectedException e) {
                    LogUtil.w(TAG, "USB interface disconnected, unable to release interface: " + e.getMessage());
                }
            }
            usbInterface = null;
            usbDevice = null;
            endpointIn = null;
            endpointOut = null;
        } catch (UsbException e) {
            LogUtil.e(TAG, "Error cleaning up USB device: " + e.getMessage());
        }
    }
}
