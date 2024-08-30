package com.pvetec.inspectra.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonBeanConverter {

    /**
     * 通用方法：将JSON字符串转换为指定类型的对象
     *
     * @param jsonString JSON字符串
     * @param beanClass  目标Bean类
     * @param <T>        Bean类型
     * @return 指定类型的对象
     */
    public static <T> T jsonToBean(String jsonString, Class<T> beanClass) {
        try {
            return JSONUtil.toBean(jsonString, beanClass);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to bean: " + e.getMessage(), e);
        }
    }

    /**
     * 通用方法：将JSON字符串转换为指定类型的对象列表
     *
     * @param jsonString JSON字符串
     * @param beanClass  目标Bean类
     * @param <T>        Bean类型
     * @return 指定类型的对象列表
     */
    public static <T> List<T> jsonToBeanList(String jsonString, Class<T> beanClass) {
        try {
            JSONArray jsonArray = JSONUtil.parseArray(jsonString);
            return JSONUtil.toList(jsonArray, beanClass);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to bean list: " + e.getMessage(), e);
        }
    }

    /**
     * 通用方法：将对象转换为JSON字符串
     *
     * @param bean 对象
     * @return JSON字符串
     */
    public static String beanToJson(Object bean) {
        try {
            return JSONUtil.toJsonStr(bean);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert bean to JSON: " + e.getMessage(), e);
        }
    }

    /**
     * 通用方法：将对象列表转换为JSON字符串
     *
     * @param beanList 对象列表
     * @param <T>      Bean类型
     * @return JSON字符串
     */
    public static <T> String beanListToJson(List<T> beanList) {
        try {
            return JSONUtil.toJsonStr(beanList);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert bean list to JSON: " + e.getMessage(), e);
        }
    }

    /**
     * 从文件中读取JSON内容并转换为指定类型的对象
     *
     * @param filePath  文件路径
     * @param beanClass 目标Bean类
     * @param <T>       Bean类型
     * @return 指定类型的对象
     * @throws IOException 文件读取异常
     */
    public static <T> T fileToBean(String filePath, Class<T> beanClass) throws IOException {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            return jsonToBean(jsonString, beanClass);
        } catch (IOException e) {
            throw new IOException("Failed to read file: " + e.getMessage(), e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to convert file content to bean: " + e.getMessage(), e);
        }
    }

    /**
     * 将指定类型的对象写入到JSON文件中
     *
     * @param bean      对象
     * @param filePath  文件路径
     * @param <T>       Bean类型
     * @throws IOException 文件写入异常
     */
    public static <T> void beanToFile(T bean, String filePath) throws IOException {
        try {
            String jsonString = beanToJson(bean);
            Files.write(Paths.get(filePath), jsonString.getBytes());
        } catch (IOException e) {
            throw new IOException("Failed to write file: " + e.getMessage(), e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to convert bean to file content: " + e.getMessage(), e);
        }
    }

    /**
     * 从文件中读取JSON内容并转换为指定类型的对象列表
     *
     * @param filePath  文件路径
     * @param beanClass 目标Bean类
     * @param <T>       Bean类型
     * @return 指定类型的对象列表
     * @throws IOException 文件读取异常
     */
    public static <T> List<T> fileToBeanList(String filePath, Class<T> beanClass) throws IOException {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
            return jsonToBeanList(jsonString, beanClass);
        } catch (IOException e) {
            throw new IOException("Failed to read file: " + e.getMessage(), e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to convert file content to bean list: " + e.getMessage(), e);
        }
    }

    /**
     * 将指定类型的对象列表写入到JSON文件中
     *
     * @param beanList  对象列表
     * @param filePath  文件路径
     * @param <T>       Bean类型
     * @throws IOException 文件写入异常
     */
    public static <T> void beanListToFile(List<T> beanList, String filePath) throws IOException {
        try {
            String jsonString = beanListToJson(beanList);
            Files.write(Paths.get(filePath), jsonString.getBytes());
        } catch (IOException e) {
            throw new IOException("Failed to write file: " + e.getMessage(), e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to convert bean list to file content: " + e.getMessage(), e);
        }
    }
}
