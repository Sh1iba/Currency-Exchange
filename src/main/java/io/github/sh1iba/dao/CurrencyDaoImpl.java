package io.github.sh1iba.dao;

import io.github.sh1iba.exception.CurrencyCodeExistsException;
import io.github.sh1iba.exception.DatabaseException;
import io.github.sh1iba.model.Currency;
import io.github.sh1iba.utils.DatabaseConnection;
import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDaoImpl implements CurrencyDao {

    @Override
    public List<Currency> getAll() {
        List<Currency> currencies = new ArrayList<>();
        String query = "SELECT ID, Code, FullName, Sign FROM Currencies";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String code = resultSet.getString("Code");
                String fullName = resultSet.getString("FullName");
                String sign = resultSet.getString("Sign");
                currencies.add(new Currency(id, code, fullName, sign));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get All currencies", e);
        }
        return currencies;
    }

    @Override
    public Currency get(String code) {
        Currency currency = null;
        String query = "SELECT ID, Code, FullName, Sign FROM Currencies WHERE Code = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String resCode = resultSet.getString("Code");
                    String fullName = resultSet.getString("FullName");
                    String sign = resultSet.getString("Sign");
                    currency = new Currency(id, resCode, fullName, sign);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get Currency by code", e);
        }
        return currency;
    }

    @Override
    public Currency insert(Currency currency) {
        String query = "INSERT INTO Currencies (Code, FullName, Sign) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getFullName());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                currency.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            if (e instanceof SQLiteException sqLiteException) {
                if (sqLiteException.getResultCode() == SQLiteErrorCode.SQLITE_CONSTRAINT_UNIQUE) {
                    throw new CurrencyCodeExistsException("A currency with this code already exists", e);
                }
            }
            throw new DatabaseException("Failed to add a new currency to the database", e);
        }

        return currency;
    }
}
