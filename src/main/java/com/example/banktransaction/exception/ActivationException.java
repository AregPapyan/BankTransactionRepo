package com.example.banktransaction.exception;

public class ActivationException extends APIRequestException{
    public ActivationException(String message) {
        super(message);
    }

    public ActivationException(String message, Throwable cause) {
        super(message, cause);
    }
}
