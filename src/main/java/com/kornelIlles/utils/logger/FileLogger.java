package com.kornelIlles.utils.logger;

import com.kornelIlles.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileLogger {

    private static Logger logger;

    static {
        configureLogger();
    }

    private static void configureLogger() {
        logger = Logger.getLogger(FileLogger.class.getName());
        String logFile = Utils.getCsvPath("application.log");
        try {
            FileHandler fileHandler = new FileHandler(logFile);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.setLevel(Level.WARNING);
    }

    public static void logWrongLine(String[] line, String errorMessage){
        logger.warning(errorMessage + " -> " + Arrays.toString(line));
    }
}
