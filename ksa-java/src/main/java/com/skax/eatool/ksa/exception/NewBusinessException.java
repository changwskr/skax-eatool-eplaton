package com.skax.eatool.ksa.exception;

/**
 * Stub class for BusinessException
 */
public class NewBusinessException extends Exception {

    public NewBusinessException() {
        super();
    }

    public NewBusinessException(String message) {
        super(message);
    }

    public NewBusinessException(String message, String code) {
        super(message + " (Code: " + code + ")");
    }

    public NewBusinessException(String message, String code, Throwable cause) {
        super(message + " (Code: " + code + ")", cause);
    }

    public NewBusinessException(Throwable cause) {
        super(cause);
    }

    public NewBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}