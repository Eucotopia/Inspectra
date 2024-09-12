package com.pvetec.inspectra.transmission.serial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.pvetec.inspectra.pojo.SerialPortConfig;
import com.pvetec.inspectra.transmission.Transmission;
import com.pvetec.inspectra.transmission.listener.DeviceConnectionListener;
import com.pvetec.inspectra.utils.JsonBeanConverter;
import com.pvetec.inspectra.utils.LogUtil;

import java.util.Arrays;


public class SerialTransmission implements Transmission {

    final static String TAG = SerialTransmission.class.getSimpleName();

    private DeviceConnectionListener connectionListener;

    private SerialPort port;

    public SerialTransmission(DeviceConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
    }

    /**
     * Opens the serial port and configures it based on the settings defined in the configuration file.
     * <p>
     * This method performs the following steps:
     * <ul>
     * <li>Retrieves the available serial ports.</li>
     * <li>Checks if there are any available serial ports and throws an exception if none are found.</li>
     * <li>Loads the serial port configuration from a JSON file using the {@link JsonBeanConverter} class.</li>
     * <li>Initializes and configures the serial port based on the loaded configuration.</li>
     * </ul>
     *
     * @throws Exception if an error occurs during the port initialization or if no serial ports are available.
     */
    @Override
    public void open() throws Exception {
        try {
            // Retrieve available serial ports
            SerialPort[] commPorts = SerialPort.getCommPorts();
            LogUtil.i(TAG, "Available serial ports retrieved: " + commPorts.length);

            // Check if there are any available serial ports
            if (commPorts.length == 0) {
                LogUtil.e(TAG, "No serial ports available.");
                throw new Exception("No serial ports available.");
            }

            // Load the serial port configuration from a JSON file
            SerialPortConfig serialPortConfig = JsonBeanConverter.fileToBean("config/ConfigSerialPort.json", SerialPortConfig.class);
            LogUtil.i(TAG, "Serial port configuration loaded: " + serialPortConfig);

            // Get the serial port based on the name from the configuration
            port = SerialPort.getCommPort(serialPortConfig.getSerialPortName());
            LogUtil.i(TAG, "Attempting to get serial port: " + serialPortConfig.getSerialPortName());

            // Check if the port was successfully obtained
            if (port == null) {
                LogUtil.e(TAG, "Serial port not found: " + serialPortConfig.getSerialPortName());
                throw new Exception("Serial port not found: " + serialPortConfig.getSerialPortName());
            } else {
                LogUtil.i(TAG, "Serial port obtained successfully.");
            }

            // Open the serial port
            if (!port.openPort()) {
                LogUtil.e(TAG, "Failed to open serial port: " + serialPortConfig.getSerialPortName());
                throw new Exception("Failed to open serial port: " + serialPortConfig.getSerialPortName());
            } else {
                LogUtil.i(TAG, "Serial port opened successfully.");
            }

            // Configure the serial port with parameters from the configuration
            port.setComPortParameters(
                    serialPortConfig.getBaudRate(),   // Baud rate
                    serialPortConfig.getDataBits(),   // Number of data bits
                    serialPortConfig.getStopBits(),   // Number of stop bits
                    serialPortConfig.getParity()      // Parity
            );
            LogUtil.i(TAG, "Serial port configured with parameters: Baud rate = " +
                    serialPortConfig.getBaudRate() + ", Data bits = " +
                    serialPortConfig.getDataBits() + ", Stop bits = " +
                    serialPortConfig.getStopBits() + ", Parity = " +
                    serialPortConfig.getParity());

            // Set the timeout parameters
            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 0, 0);
            LogUtil.i(TAG, "Serial port timeouts set to TIMEOUT_READ_BLOCKING.");

            Thread.sleep(1000);

            // Add a data listener to handle events
            port.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    // Define the events to listen for: data availability and port disconnection
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE |
                            SerialPort.LISTENING_EVENT_PORT_DISCONNECTED;
                }

                @Override
                public void serialEvent(SerialPortEvent serialPortEvent) {
                    switch (serialPortEvent.getEventType()) {
                        case SerialPort.LISTENING_EVENT_DATA_AVAILABLE:
                            // Log message when data is available
                            LogUtil.i(TAG, "Data available event triggered.");

                            // Get the number of bytes available for reading
                            int availableBytes = port.bytesAvailable();
                            LogUtil.i(TAG, "Number of available data bytes: " + availableBytes);

                            if (availableBytes > 0) {
                                // Read the data
                                byte[] data = new byte[availableBytes];
                                int bytesRead = port.readBytes(data, data.length);
                                LogUtil.i(TAG, "Read " + bytesRead + " bytes of data from the serial port.");
                                // Log the received data content; if the data is large, consider logging only part of it
                                LogUtil.i(TAG, "Received data: " + Arrays.toString(data));

                                // Process the received data (implementation not shown)
                            } else {
                                LogUtil.i(TAG, "No bytes available to read.");
                            }

                            // Notify the connection listener that data is available
                            connectionListener.onDeviceConnected("Data available");
                            break;

                        case SerialPort.LISTENING_EVENT_PORT_DISCONNECTED:
                            // Log message when the port is disconnected
                            LogUtil.i(TAG, "Port disconnected event triggered.");

                            // Notify the listener and record the port disconnection
                            connectionListener.onDeviceDisconnected();
                            break;

                        default:
                            // Log unknown event types
                            LogUtil.i(TAG, "Unhandled event type: " + serialPortEvent.getEventType());
                            break;
                    }
                }
            });

        } catch (Exception e) {
            // Log and rethrow any exceptions encountered during port initialization
            LogUtil.e(TAG, "Error during port initialization: " + e.getMessage());
            throw e; // Rethrow the exception to be handled by the caller
        }
    }


    @Override
    public void close() throws Exception {
        if (port != null) {
            port = null;
        }
        if (connectionListener != null) {
            connectionListener = null;
        }
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
