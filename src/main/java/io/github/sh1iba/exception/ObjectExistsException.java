package io.github.sh1iba.exception;

public class ObjectExistsException extends RuntimeException {
    public ObjectExistsException(String message) {
        super(message);
    }

    public ObjectExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
