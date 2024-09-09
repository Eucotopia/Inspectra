package com.pvetec.inspectra.enums;

import lombok.Getter;

@Getter
public enum ProjectType {
    BM400(TransmissionType.USB),
    DEFAULT_PROJECT(TransmissionType.SERIAL);

    private final TransmissionType communicationType;

    ProjectType(TransmissionType communicationType) {
        this.communicationType = communicationType;
    }

    public static ProjectType fromName(String projectName) {
        for (ProjectType type : values()) {
            if (projectName.contains(type.name())) {
                return type;
            }
        }
        return DEFAULT_PROJECT; // 默认项目类型
    }
}