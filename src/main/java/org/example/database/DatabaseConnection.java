package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

        private static final String URL = "jdbc:mysql://localhost:3308/test";
        private static final String USER = "root";       // your MySQL username
        private static final String PASSWORD = "root123"; // your MySQL password

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }
