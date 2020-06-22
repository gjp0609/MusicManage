package com.onysakura;

import com.onysakura.utils.CustomLogger;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

public class LoggerTest {
    Logger logger = CustomLogger.getLogger(LoggerTest.class);

    @Test
    public void testOut() {
        logger.severe("severe");
        logger.warning("warning");
        logger.info("info");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }

    @Test
    public void color() {
        for (int i = 0; i < 100; i++) {
            System.out.println("\033[" + i + "m color+\033[0m " + i);
        }
    }
}
