package io.github.sh1iba.dao;

import io.github.sh1iba.exception.DatabaseException;
import io.github.sh1iba.model.Currency;
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
        String query = """
                SELECT er.ID, a.ID AS BaseID,  a.Code AS BaseCode, a.FullName AS BaseFullName, a.Sign AS BaseSign, 
                       b.ID AS TargetID, b.Code AS TargetCode, b.FullName AS TargetFullName, b.Sign AS TargetSign, 
                       er.Rate FROM ExchangeRates er JOIN Currencies a ON er.BaseCurrencyId = a.ID JOIN Currencies b 
                       ON er.TargetCurrencyId = b.ID
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                exchangeRateList.add(getData(resultSet));
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
                SELECT er.ID, a.ID AS BaseID,  a.Code AS BaseCode, a.FullName AS BaseFullName, a.Sign AS BaseSign, 
                       b.ID AS TargetID, b.Code AS TargetCode, b.FullName AS TargetFullName, b.Sign AS TargetSign, 
                       er.Rate FROM ExchangeRates er JOIN Currencies a ON er.BaseCurrencyId = a.ID AND a.Code = ? 
                           JOIN Currencies b ON er.TargetCurrencyId = b.ID AND b.Code = ?
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    exchangeRate = getData(resultSet);
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
            preparedStatement.setInt(1, exchangeRate.getBaseCurrency().getId());
            preparedStatement.setInt(2, exchangeRate.getTargetCurrency().getId());
            preparedStatement.setBigDecimal(3, exchangeRate.getRate());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    exchangeRate.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to add new exchange rate to the database", e);
        }
        return exchangeRate;
    }

    @Override
    public ExchangeRate update(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) {
        ExchangeRate exchangeRate = get(baseCurrencyCode, targetCurrencyCode);
        String query = "UPDATE ExchangeRates SET Rate = ? WHERE ID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setBigDecimal(1, rate);
            preparedStatement.setInt(2, exchangeRate.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Failed to update exchange rate", e);
        }
        exchangeRate.setRate(rate);
        return exchangeRate;
    }

    private ExchangeRate getData(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("ID");
        int baseID = resultSet.getInt("BaseID");
        String baseCode = resultSet.getString("BaseCode");
        String baseFullName = resultSet.getString("BaseFullName");
        String baseSign = resultSet.getString("BaseSign");
        int targetID = resultSet.getInt("TargetID");
        String targetCode = resultSet.getString("TargetCode");
        String targetFullName = resultSet.getString("TargetFullName");
        String targetSign = resultSet.getString("TargetSign");
        BigDecimal rate = resultSet.getBigDecimal("Rate");
        return new ExchangeRate(id, new Currency(baseID, baseCode, baseFullName, baseSign),
                new Currency(targetID, targetCode, targetFullName, targetSign), rate);
    }

}
