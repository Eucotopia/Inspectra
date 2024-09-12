package com.pvetec.inspectra.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author LIWEI
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Platform {
    private String name;
    private List<Project> projects;
}