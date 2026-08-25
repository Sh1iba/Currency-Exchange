package io.github.sh1iba.dao;

/* TODO
    GET - получение всех обменных курсов
    GET - получение конкретного обменного курса (Валютная пара задаётся идущими подряд кодами валют в адресе запроса /USDRUB)
    POST - добавление нового обменного курса в базу
    PATCH - обновление существующего в базе обменного курса
    GET - расчёт перевода определённого количества средств из одной валюты в другую
*/

import io.github.sh1iba.model.ExchangeRate;

import java.sql.SQLException;
import java.util.List;

public interface ExchangeRateDao {

    List<ExchangeRate> getAll() throws SQLException;

    ExchangeRate get(String pairOfCurrencyCodes) throws SQLException;

    ExchangeRate insert(ExchangeRate exchangeRate) throws SQLException;

    ExchangeRate update(ExchangeRate exchangeRate) throws SQLException;


}
