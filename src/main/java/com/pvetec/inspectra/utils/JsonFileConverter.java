package com.pvetec.inspectra.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;

public class JsonFileConverter {

    public static <T> T readJsonFile(String filePath, Class<T> valueType) {
        // 读取文件为字符串
        String jsonContent = FileUtil.readUtf8String(filePath);

        // 将 JSON 字符串转换为 Java 对象
        return JSONUtil.toBean(jsonContent, valueType);
    }

    public static <T> void writeJsonFile(String filePath, T object) {
        // 将 Java 对象转换为 JSON 字符串
        String jsonString = JSONUtil.toJsonStr(object);

        // 将 JSON 字符串写入文件
        FileUtil.writeString(jsonString, filePath, "UTF-8");
    }
}

