package com.pvetec.inspectra.pojo;

import com.pvetec.inspectra.enums.StationEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author LIWEI
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentTest {
    // TODO 不要使用构造方法，因为无法保证 stationName 为 StationEnum 的值
    // 当前处在哪个占位
    private String stationName;

    // 项目名称
    private String projectName;

    private TestType testType;

    /**
     * Sets the station name if it's valid according to StationEnum.
     *
     * @param stationName The name of the station to set.
     * @throws IllegalArgumentException if the station name is not valid.
     */
    public void setStationName(String stationName) {
        if (StationEnum.fromName(stationName) == null) {
            throw new IllegalArgumentException("Invalid station name: " + stationName);
        }
        this.stationName = stationName;
    }
}
