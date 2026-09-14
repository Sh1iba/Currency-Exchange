import io.github.sh1iba.dao.CurrencyDao;
import io.github.sh1iba.dao.CurrencyDaoImpl;
import io.github.sh1iba.exception.DatabaseException;
import io.github.sh1iba.model.Currency;
import io.github.sh1iba.utils.DatabaseConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    private static void connect() {
        try (var connection = DatabaseConnection.getConnection()) {
            System.out.println("Connection to database is successful");
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred", e);
        }
    }

    public static void main(String[] args) {
        //getCurrencyByCode("USD");
        insertCurrency(new Currency("CZK", "Czech Koruna", "Kč"));
    }

    private static void getAllCurrencies() {
        CurrencyDao currencyDao = new CurrencyDaoImpl();
        List<Currency> list = new ArrayList<>();
        list = currencyDao.getAll();
        for (Currency c : list) {
            System.out.println(c);
        }
    }

    private static void getCurrencyByCode(String code) {
        CurrencyDao currencyDao = new CurrencyDaoImpl();
        Currency currency = currencyDao.get(code);
        System.out.println(currency);
    }

    private static void insertCurrency(Currency currency){
        CurrencyDao currencyDao = new CurrencyDaoImpl();
        currency = currencyDao.insert(currency);
        System.out.println(currency);
    }
}
