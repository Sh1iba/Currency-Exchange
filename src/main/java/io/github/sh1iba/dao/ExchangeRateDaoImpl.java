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
    public ExchangeRate get(String pairOfCurrencyCodes) {
        return null;
    }

    @Override
    public ExchangeRate insert(ExchangeRate exchangeRate) {
        return null;
    }

    @Override
    public ExchangeRate update(ExchangeRate exchangeRate) {
        return null;
    }
}
