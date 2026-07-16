package io.github.sh1iba;

import java.sql.SQLException;

public class JdbcRunner {

    public static void connect() {
        try (var connection = ConnectionManager.open()) {
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void main(String[] args) {
        JdbcRunner.connect();
    }
}
