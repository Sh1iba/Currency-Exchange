package io.github.sh1iba.utils;

import io.github.sh1iba.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
    private static final String URL_KEY = "db.url";

    private DatabaseConnection() {
    }

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("SQLite driver not found", e);
        }
    }

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(PropertiesUtil.get(URL_KEY));
        } catch (SQLException e) {
            throw new DatabaseException("Failed to connect to database", e);

        }
    }
}
