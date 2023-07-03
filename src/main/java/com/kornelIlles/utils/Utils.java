package com.kornelIlles.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Utils {

    private static Properties properties = new Properties();

    public static Date stringToDate(String dateString) {
        String dateFormat = "yyyy.MM.dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            sdf.setLenient(false);
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream getCsvInputStream(String path) throws FileNotFoundException {
        try {
            // Check if the path is an absolute file path
            File file = new File(path);
            if (file.isAbsolute()) {
                return new FileInputStream(file);
            }

            // Check if the path is relative to the current working directory
            File cwdFile = new File(path);
            if (cwdFile.exists()) {
                return new FileInputStream(cwdFile);
            }

            // Get the base directory of the JAR file
            CodeSource codeSource = Utils.class.getProtectionDomain().getCodeSource();
            if (codeSource != null) {
                File jarFile = new File(codeSource.getLocation().toURI());
                File baseDir = jarFile.getParentFile();

                // Create the file using the base directory and path
                File csvFile = new File(baseDir, path);
                if (csvFile.exists()) {
                    return new FileInputStream(csvFile);
                }
            }

            // Attempt to find the file as a resource on the classpath
            InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream(path);
            if (inputStream != null) {
                return inputStream;
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        throw new FileNotFoundException("File not found: " + path);
    }



    public static OutputStream getCsvOutputStream(String path) {
        try {
            File file = new File(path);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs(); // Creates the directories if they don't exist
            }
            return new FileOutputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCsvPath(String path) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("config.properties");
            Properties properties = new Properties();
            properties.load(input);
            return properties.getProperty(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
