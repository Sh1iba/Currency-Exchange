package io.github.sh1iba.dao;

import io.github.sh1iba.model.Currency;

import java.sql.SQLException;
import java.util.List;

public class CurrencyDaoImpl implements CurrencyDao{

    @Override
    public List<Currency> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public Currency get(String code) throws SQLException {
        return null;
    }

    @Override
    public Currency insert(Currency currency) throws SQLException {
        return null;
    }
}
