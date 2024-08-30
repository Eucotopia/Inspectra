package com.pvetec.inspectra.pojo;

import lombok.Data;

import java.util.List;

@Data
public class TestType {
    private String name;
    // PCBA 测试、验号测试、等等 code
    private int code;
    // 表示当前的测试项
    private List<TestItem> items;
}
