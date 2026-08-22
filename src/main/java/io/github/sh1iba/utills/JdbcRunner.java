package io.github.sh1iba.utills;

import java.sql.SQLException;

public class JdbcRunner {

    public static void connect() {
        try (var connection = ConnectionManager.open()) {
            System.out.println("Соединение с БД успешно установлено");
        } catch (SQLException e) {
            System.err.println("Не удалось установить соединение с БД");
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        JdbcRunner.connect();
    }

}
