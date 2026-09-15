package io.github.sh1iba.dao;

import io.github.sh1iba.exception.DatabaseException;
import io.github.sh1iba.model.ExchangeRate;
import io.github.sh1iba.utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDaoImpl implements ExchangeRateDao {

    @Override
    public List<ExchangeRate> getAll() {
        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        String query = "SELECT ID, BaseCurrencyId, TargetCurrencyId, Rate FROM ExchangeRates";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int baseCurrencyId = resultSet.getInt("BaseCurrencyId");
                int targetCurrencyId = resultSet.getInt("TargetCurrencyId");
                BigDecimal rate = resultSet.getBigDecimal("Rate");
                exchangeRateList.add(new ExchangeRate(id, baseCurrencyId, targetCurrencyId, rate));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get all Exchange rate", e);
        }
        return exchangeRateList;

    }

    @Override
    public ExchangeRate get(String baseCurrencyCode, String targetCurrencyCode) {
        ExchangeRate exchangeRate = null;
        String query = """ 
                SELECT er.ID, er.BaseCurrencyId, er.TargetCurrencyId, er.Rate FROM ExchangeRates er 
                JOIN Currencies a ON er.BaseCurrencyId = a.ID AND a.Code = ? 
                JOIN Currencies b ON er.TargetCurrencyId = b.ID AND b.Code = ?
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int baseCurrencyId = resultSet.getInt("BaseCurrencyId");
                    int targetCurrencyId = resultSet.getInt("TargetCurrencyId");
                    BigDecimal rate = resultSet.getBigDecimal("Rate");
                    exchangeRate = new ExchangeRate(id, baseCurrencyId, targetCurrencyId, rate);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get Exchange rate by codes", e);
        }
        return exchangeRate;
    }

    @Override
    public ExchangeRate insert(ExchangeRate exchangeRate) {
        String query = "INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, exchangeRate.getBaseCurrencyId());
            preparedStatement.setInt(2, exchangeRate.getTargetCurrencyId());
            preparedStatement.setBigDecimal(3, exchangeRate.getRate());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                exchangeRate.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to add new exchange rate to the database", e);
        }
        return exchangeRate;
    }

    @Override
    public ExchangeRate update(ExchangeRate exchangeRate) {
        return null;
    }
}
