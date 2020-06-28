package com.onysakura;

import com.onysakura.utils.CustomLogger;
import org.junit.jupiter.api.Test;

public class LoggerTest {
    CustomLogger.Log logger = CustomLogger.getLogger(LoggerTest.class);

    @Test
    public void testOut() {
        logger.error("error");
        logger.warn("warn");
        logger.info("info");
        logger.debug("debug");
    }

    @Test
    public void color() {
        for (int i = 0; i < 100; i++) {
            System.out.println("\033[" + i + "m color+\033[0m " + i);
        }
    }
}
