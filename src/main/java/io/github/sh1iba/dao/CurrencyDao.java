package io.github.sh1iba.dao;

import io.github.sh1iba.model.Currency;

import java.util.List;
import java.util.Optional;

/* TODO
    GET - получение всех валют
    GET - получение валюты по коду
    POST - добавление новой валюты в базу
*/
public interface CurrencyDao {

    List<Currency> getAll();

    Optional<Currency> get(String code);

    Currency insert(Currency currency);
}
