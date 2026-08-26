package io.github.sh1iba.utils;

import io.github.sh1iba.exception.DatabaseException;

import java.sql.SQLException;

public class JdbcRunner {

    public static void connect() {
        try (var connection = ConnectionManager.open()) {
            System.out.println("Connection to database is successful");
        } catch (SQLException e) {
            throw new DatabaseException("Failed to close database connection", e);
        }
    }

}
