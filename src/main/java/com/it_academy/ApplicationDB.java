package com.it_academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ApplicationDB {

    private static final String DB_URL =
            "jdbc:sqlite:" + System.getProperty("user.dir") +
                    "\\src\\main\\resources\\UserAccounts.db";

    public static void main(String[] args) {
        System.out.println(isDriverExists());
        System.out.println(DB_URL);
        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            DBQueryMenu.menu(connection);
        } catch (SQLException e) {
            System.out.println("ooops connection failed :,(");
        }
    }

    public static boolean isDriverExists() {
        try {
            Class.forName("org.sqlite.JDBC");
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("Driver does not exists");
            return false;
        }
    }
}
