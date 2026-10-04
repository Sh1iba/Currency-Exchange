package io.github.sh1iba.validation;

import io.github.sh1iba.exception.IncorrectRequestException;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class Validation {
    private static final Pattern LETTER = Pattern.compile("^[A-Z]+$");
    private static final int CODE_LENGTH = 3;
    private static final int PAIR_CODES_LENGTH = 6;

    public static void formValidation(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IncorrectRequestException("The required '" + name + "' form field is missing");
        }
    }

    public static void pathValidation(String path, String message) throws IncorrectRequestException {
        if (path == null || path.equals("/")) {
            throw new IncorrectRequestException(message);
        }
    }

    public static void codeValidation(String code) throws IncorrectRequestException {
        if (code.length() != CODE_LENGTH || !code.equals(code.toUpperCase()) || isNotLetter(code)) {
            throw new IncorrectRequestException("Invalid currency code format. Currency code must be exactly "
                    + CODE_LENGTH + " uppercase letters. Example : 'USD'");
        }
    }

    public static void pairOfCodesValidation(String code) throws IncorrectRequestException {
        if (code.length() != PAIR_CODES_LENGTH || !code.equals(code.toUpperCase()) || isNotLetter(code)) {
            throw new IncorrectRequestException("Invalid exchange rate format. Exchange rate must be exactly " +
                    PAIR_CODES_LENGTH + " uppercase letters. Example : 'USDRUB'");
        }
    }

    public static void rateValidation(String strRate){
        try {
            BigDecimal rate = new BigDecimal(strRate);
        } catch (NumberFormatException e) {
            throw new IncorrectRequestException("Rate must be a valid number");
        }
    }

    public static boolean isNotLetter(String code) {
        return !LETTER.matcher(code).matches();
    }

}
