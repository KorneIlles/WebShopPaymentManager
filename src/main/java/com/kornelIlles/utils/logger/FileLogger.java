package com.kornelIlles.utils.logger;

import com.kornelIlles.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.*;

public class FileLogger {

    private static Logger logger;

    static {
        configureLogger();
    }

    private static void configureLogger() {
        logger = Logger.getLogger(FileLogger.class.getName());
        LogManager.getLogManager().reset();
        String logFile = Utils.getCsvPath("application.log");
        try {
            createLogFolderIfNotExist(logFile);
            FileHandler fileHandler = new FileHandler(logFile);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.setLevel(Level.WARNING);
    }

    private static void createLogFolderIfNotExist(String logFile) {
        File logFolder = new File(logFile).getParentFile();
        if (!logFolder.exists()) {
            logFolder.mkdirs(); // Creates the log folder if it doesn't exist
        }
    }

    public static void logWrongLine(String[] line, String errorMessage) {
        logger.warning(errorMessage + " -> " + Arrays.toString(line));
    }
}
