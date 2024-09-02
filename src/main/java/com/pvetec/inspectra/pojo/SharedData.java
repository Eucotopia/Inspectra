package com.pvetec.inspectra.pojo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class SharedData {

    private final BooleanProperty deviceConnected = new SimpleBooleanProperty(false);

    private final BooleanProperty operationFinished = new SimpleBooleanProperty(false);


    public BooleanProperty deviceConnectedProperty() {
        return deviceConnected;
    }

    public boolean isDeviceConnected() {
        return deviceConnected.get();
    }

    public void setDeviceConnected(boolean connected) {
        this.deviceConnected.set(connected);
    }

    public BooleanProperty operationFinishedProperty() {
        return operationFinished;
    }

    public boolean isOperationFinished() {
        return operationFinished.get();
    }

    public void setOperationFinished(boolean finished) {
        this.operationFinished.set(finished);
    }
}
