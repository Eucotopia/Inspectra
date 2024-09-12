package com.pvetec.inspectra.pojo;

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

    // 当前处在哪个占位
    private String stationName;

    // 项目名称
    private String projectName;

    private TestType testType;

}
