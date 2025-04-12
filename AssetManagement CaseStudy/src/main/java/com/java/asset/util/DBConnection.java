package com.java.asset.util;

import java.sql.Connection;

public class DBConnection {

    public static Connection getConnection() {
       
        return DBConnUtil.getConnection();
    }
}
