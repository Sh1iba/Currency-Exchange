package io.github.sh1iba.dao;

import io.github.sh1iba.exception.DatabaseException;
import io.github.sh1iba.model.Currency;
import io.github.sh1iba.utils.DatabaseConnection;

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
            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                String code = resultSet.getString("Code");
                String fullName = resultSet.getString("FullName");
                String sign = resultSet.getString("Sign");
                currencies.add(new Currency(id,code,fullName,sign));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get All currencies", e);
        }
        return currencies;
    }

    @Override
    public Currency get(String code) {
        return null;
    }

    @Override
    public Currency insert(Currency currency) {
        return null;
    }
}
