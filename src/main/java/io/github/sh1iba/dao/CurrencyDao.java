package io.github.sh1iba.dao;

import io.github.sh1iba.model.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyDao {

    List<Currency> getAll();

    Optional<Currency> get(String code);

    Currency insert(Currency currency);
}
