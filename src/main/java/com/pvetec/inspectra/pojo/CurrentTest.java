package com.pvetec.inspectra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentTest {
    // 当前处在哪个占位
    private Integer stationId;
    // 项目名称
    private String name;

    private TestType testType;
}
