package com.kornelIlles.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Utils {

    private static Properties properties = new Properties();

    public static Date stringToDate(String dateString){
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

    public static String getCsvPath(String path){
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
            return properties.getProperty(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
