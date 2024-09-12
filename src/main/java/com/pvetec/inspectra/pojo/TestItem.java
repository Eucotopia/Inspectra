package com.pvetec.inspectra.pojo;

import com.pvetec.inspectra.enums.TestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a test item with detailed information used for quality inspection.
 * <p>
 * This class captures all relevant details about a test item including its code,
 * name, result, description, timestamp, and status. The status of the test item
 * is represented using an enum {@link TestStatus} to ensure type safety and clarity.
 * </p>
 * @author LIWEI
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestItem {
    /**
     * The unique code identifying the test item.
     * <p>
     * This code is used to reference and categorize different test items.
     * </p>
     */
    private Integer code;

    /**
     * The name of the test item.
     * <p>
     * Provides a descriptive name for the test item which is useful for identification.
     * </p>
     */
    private String name;

    /**
     * The result of the test item.
     * <p>
     * This field stores the outcome of the test, such as "Pass", "Fail", or specific measurement results (e.g., "5V").
     * </p>
     */
    private String result;

    /**
     * A description of the test item.
     * <p>
     * Provides additional details or context about the test item and what it measures.
     * </p>
     */
    private String description;

    /**
     * The status of the test item.
     * <p>
     * Indicates the current status of the test item. The status is represented by an enum {@link TestStatus} with possible values:
     * {@link TestStatus#PASSED}, {@link TestStatus#FAILED}, {@link TestStatus#NOT_TESTED}.
     * </p>
     */
    private TestStatus status;
}
