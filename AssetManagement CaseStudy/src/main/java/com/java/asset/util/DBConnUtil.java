package com.java.asset.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class DBConnUtil {

    public static Connection getConnection() {
        Properties properties = new Properties();
        String url = null;
        String user = null;
        String password = null;

        try (InputStream input = DBConnUtil.class.getClassLoader().getResourceAsStream("asset.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find asset.properties");
                return null;
            }
            properties.load(input);
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database Connected Successfully");
        } catch (SQLException e) {
            System.out.println("Database Connection Failed");
            e.printStackTrace();
        }
        return connection;
    }
}