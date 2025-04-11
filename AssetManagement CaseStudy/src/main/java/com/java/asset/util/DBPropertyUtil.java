package com.java.asset.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBPropertyUtil {

    public static String getConnectionUrl(String propertyFileName) {
        Properties properties = new Properties();
        try (InputStream input = DBPropertyUtil.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + propertyFileName);
                return null;
            }
            properties.load(input);
            return properties.getProperty("url");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}