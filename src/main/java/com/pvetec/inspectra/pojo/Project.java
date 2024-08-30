package com.pvetec.inspectra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    private String name;
    private Integer code;
    private List<TestType> testTypes;
}