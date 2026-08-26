package io.github.sh1iba.dao;

import io.github.sh1iba.model.Currency;

import java.sql.SQLException;
import java.util.List;

/* TODO
    GET - получение всех валют
    GET - получение валюты по коду
    POST - добавление новой валюты в базу
*/
public interface CurrencyDao {

    List<Currency> getAll() throws SQLException;

    Currency get(String code) throws SQLException;

    Currency insert(Currency currency) throws SQLException;
}
