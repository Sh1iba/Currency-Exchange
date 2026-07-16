package io.github.sh1iba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final String URL_KEY = "db.url";

    private ConnectionManager() {
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(PropertiesUtil.get(URL_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
