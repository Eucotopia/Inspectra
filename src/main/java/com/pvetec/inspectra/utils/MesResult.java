package com.pvetec.inspectra.pojo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.pvetec.inspectra.enums.TestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents the result of test items to be recorded in the MES system.
 * <p>
 * This class contains details about the test batch, test station, timestamp, tester,
 * and the status of the tests, and provides methods for JSON conversion and uploading
 * to the MES system.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MesResult {
    /**
     * The production batch ID for the tests.
     * <p>
     * Used to group test results by production batch.
     * </p>
     */
    private String batchId;

    /**
     * The ID of the test station where the tests were conducted.
     * <p>
     * Identifies the location or equipment used for testing.
     * </p>
     */
    private String stationId;

    /**
     * The timestamp when the tests were conducted.
     * <p>
     * Used for logging and tracking when the tests occurred.
     * </p>
     */
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * The list of test items and their results.
     * <p>
     * Contains detailed results for each test item.
     * </p>
     */
    private List<TestItem> testItems;

    /**
     * The status of the test results.
     * <p>
     * Indicates whether the tests passed, failed, or were not conducted.
     * </p>
     */
    private TestStatus status;

    /**
     * Additional remarks or description of the test results.
     * <p>
     * Used to provide any extra information or failure descriptions.
     * </p>
     */
    private String remarks;

    /**
     * Converts the MesResult object to a JSON string.
     *
     * @return The JSON representation of the MesResult.
     */
    public String toJson() {
        return JSONUtil.toJsonStr(this);
    }

    /**
     * Converts a JSON string to a MesResult object.
     *
     * @param json The JSON string to parse.
     * @return The MesResult object.
     */
    public static MesResult fromJson(String json) {
        return JSONUtil.toBean(json, MesResult.class);
    }

    /**
     * Uploads the result to the MES system.
     *
     * @param mesUrl The URL of the MES system endpoint.
     * @return The response from the MES system.
     */
    public String uploadToMes(String mesUrl) {
        String jsonPayload = toJson();

        HttpResponse response = HttpRequest.post(mesUrl)
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .execute();

        return response.body();
    }
}
