package io.github.sh1iba.exception;

public class CurrencyCodeExistsException extends RuntimeException {
    public CurrencyCodeExistsException(String message) {
        super(message);
    }

    public CurrencyCodeExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
