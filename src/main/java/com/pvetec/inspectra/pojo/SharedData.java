package com.pvetec.inspectra.pojo;

import javafx.beans.property.*;

/**
 * This class encapsulates shared data that can be used across different parts of the application.
 * It provides properties to manage and observe the state of device connectivity, operation status,
 * and the station property.
 */
public class SharedData {

    // Property to track if the device is connected.
    private final BooleanProperty deviceConnected = new SimpleBooleanProperty(false);

    // Property to track if the operation has been completed.
    private final BooleanProperty operationFinished = new SimpleBooleanProperty(false);

    // Property to hold and track the station number.
    private final SimpleIntegerProperty stationProperty = new SimpleIntegerProperty();

    /**
     * Returns the BooleanProperty for device connectivity.
     * This property can be bound to UI components to reflect the connectivity status.
     *
     * @return The BooleanProperty representing the device connectivity status.
     */
    public BooleanProperty deviceConnectedProperty() {
        return deviceConnected;
    }

    /**
     * Returns the SimpleIntegerProperty for the station number.
     * This property can be bound to UI components to reflect the current station.
     *
     * @return The SimpleIntegerProperty representing the station number.
     */
    public SimpleIntegerProperty stationProperty() {
        return stationProperty;
    }

    /**
     * Sets the value of the station property.
     *
     * @param station The new station number to set.
     */
    public void setStationProperty(Integer station) {
        stationProperty.set(station);
    }

    /**
     * Sets the device connectivity status.
     *
     * @param connected The new connectivity status (true for connected, false for not connected).
     */
    public void setDeviceConnected(boolean connected) {
        this.deviceConnected.set(connected);
    }

    /**
     * Returns the BooleanProperty for operation completion status.
     * This property can be bound to UI components to reflect whether the operation is finished.
     *
     * @return The BooleanProperty representing the operation completion status.
     */
    public BooleanProperty operationFinishedProperty() {
        return operationFinished;
    }

    /**
     * Sets the operation completion status.
     *
     * @param finished The new operation status (true for finished, false for not finished).
     */
    public void setOperationFinished(boolean finished) {
        this.operationFinished.set(finished);
    }
}