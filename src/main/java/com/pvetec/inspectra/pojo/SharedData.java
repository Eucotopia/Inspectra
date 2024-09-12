package com.pvetec.inspectra.pojo;

import com.pvetec.inspectra.enums.StationEnum;
import javafx.beans.property.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class SharedData {

    private final BooleanProperty deviceConnected = new SimpleBooleanProperty(false);

    private final BooleanProperty operationFinished = new SimpleBooleanProperty(false);


    private final SimpleObjectProperty<StationEnum> stationEnumProperty = new SimpleObjectProperty<>();

    public BooleanProperty deviceConnectedProperty() {
        return deviceConnected;
    }


    public SimpleObjectProperty<StationEnum> stationEnumSimpleObjectProperty(){
        return stationEnumProperty;
    }

    public boolean isDeviceConnected() {
        return deviceConnected.get();
    }

    public void setDeviceConnected(boolean connected) {
        this.deviceConnected.set(connected);
    }

    public void setStationEnumProperty(StationEnum stationEnumProperty){
        this.stationEnumProperty.set(stationEnumProperty);
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
