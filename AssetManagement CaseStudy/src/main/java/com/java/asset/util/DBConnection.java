package com.java.asset.util;

import java.sql.Connection;

public class DBConnection {

    public static Connection getConnection() {
        // Directly return a new connection every time it's requested
        return DBConnUtil.getConnection();
    }
}