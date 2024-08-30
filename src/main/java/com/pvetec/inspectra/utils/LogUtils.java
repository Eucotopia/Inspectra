package com.pvetec.inspectra.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogUtils {
    private static final Logger logger = LoggerFactory.getLogger("PveTec");

    public static void i(String tag, String msg) {
        logger.info("{} - {}", tag, msg);
    }

    public static void d(String tag, String msg) {
        logger.debug("{} - {}", tag, msg);
    }

    public static void e(String tag, String msg) {
        logger.error("{} - {}", tag, msg);
    }

    public static void w(String tag, String msg) {
        logger.warn("{} - {}", tag, msg);
    }
}
