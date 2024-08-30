package com.pvetec.inspectra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentTest {
    private String name;
    private TestType testType;
}
