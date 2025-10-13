package com.example.ad_management.exceptions;

public class ValidationException extends Exception{

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
