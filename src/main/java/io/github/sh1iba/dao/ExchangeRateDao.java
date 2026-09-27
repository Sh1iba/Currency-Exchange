package io.github.sh1iba.dao;

/* TODO
    GET - получение всех обменных курсов
    GET - получение конкретного обменного курса (Валютная пара задаётся идущими подряд кодами валют в адресе запроса /USDRUB)
    POST - добавление нового обменного курса в базу
    PATCH - обновление существующего в базе обменного курса
    GET - расчёт перевода определённого количества средств из одной валюты в другую
*/

import io.github.sh1iba.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ExchangeRateDao {

    List<ExchangeRate> getAll();

    Optional<ExchangeRate> get(String baseCurrencyCode, String targetCurrencyCode);

    ExchangeRate insert(ExchangeRate exchangeRate);

    ExchangeRate update(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate);
}
