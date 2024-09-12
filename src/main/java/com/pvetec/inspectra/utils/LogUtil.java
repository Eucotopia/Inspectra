package com.pvetec.inspectra.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
    private static final Logger logger = LoggerFactory.getLogger("PveTec");

    public static void i(String tag, String message) {
        logger.info(formatMessage(tag, message));
    }

    public static void d(String tag, String message) {
        logger.debug(formatMessage(tag, message));
    }

    public static void e(String tag, String message) {
        logger.error(formatMessage(tag, message));
    }

    public static void w(String tag, String message) {
        logger.warn(formatMessage(tag, message));
    }

    public static void e(String tag, String message, Throwable throwable) {
        logger.error(formatMessage(tag, message), throwable);
    }

    public static void w(String tag, String message, Throwable throwable) {
        logger.warn(formatMessage(tag, message), throwable);
    }

    public static void highlight(String tag, String message) {
        logger.info(formatHighlightedMessage(tag, message));
    }

    private static String formatMessage(String tag, String message) {
        return String.format("[%s] %s", tag, message);
    }

    private static String formatHighlightedMessage(String tag, String message) {
        return String.format("IMPORTANT: [%s] %s", tag, message);
    }
}