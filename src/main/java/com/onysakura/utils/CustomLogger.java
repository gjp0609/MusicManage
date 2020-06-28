package com.onysakura.utils;

import com.onysakura.constans.Properties;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

public class CustomLogger {

    private static final int CLASS_NAME_LENGTH_LIMIT = 30;
    private static final boolean IS_SAVE_LOG_FILE = false;

    public static Log getLogger(Class<?> loggerName) {
        Logger logger = Logger.getLogger(loggerName.getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        Formatter formatter = new Formatter() {
            @Override
            public String format(LogRecord record) {
                return getColor(record.getLevel()) +
                        DateUtils.format(new Date(record.getMillis()), DateUtils.YYYY_MM_DD_HH_MM_SS) + " "
                        + "[" + String.format("%5s", getLevel(record.getLevel())) + "] "
                        + getShortClassName(record.getLoggerName()) + ": "
                        + record.getMessage() + "\033[0m\n";
            }
        };
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        if (IS_SAVE_LOG_FILE) {
            try {
                FileHandler fileHandler = new FileHandler(Properties.FILE_PATH + "/" + DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSS) + ".log");
                fileHandler.setFormatter(formatter);
                fileHandler.setLevel(Level.ALL);
                logger.addHandler(fileHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new Log(logger);
    }

    public static class Log {
        private final Logger logger;

        public Log(Logger logger) {
            this.logger = logger;
        }

        public void debug(Object msg) {
            logger.fine(String.valueOf(msg));
        }

        public void info(Object msg) {
            logger.info(String.valueOf(msg));
        }

        public void warn(Object msg) {
            logger.warning(String.valueOf(msg));
        }

        public void error(Object msg) {
            logger.severe(String.valueOf(msg));
        }
    }

    private static String getLevel(Level level) {
        return switch (level.getName()) {
            case "SEVERE" -> "ERROR";
            case "WARNING" -> "WARN";
            case "INFO" -> "INFO";
            case "FINE", "FINER", "FINEST" -> "DEBUG";
            default -> "DEBUG";
        };
    }

    private static String getShortClassName(String className) {
        if (className.length() <= CustomLogger.CLASS_NAME_LENGTH_LIMIT) {
            return String.format("%" + CustomLogger.CLASS_NAME_LENGTH_LIMIT + "s", className);
        }
        return String.format("%" + CustomLogger.CLASS_NAME_LENGTH_LIMIT + "s", className.substring(className.lastIndexOf(".") + 1));
    }

    private static String getColor(Level level) {
        return switch (level.getName()) {
            case "SEVERE" -> "\033[31m";
            case "WARNING" -> "\033[33m";
            case "INFO" -> "\033[30m";
            case "FINE", "FINER", "FINEST" -> "\033[37m";
            default -> "DEBUG";
        };
    }
}
