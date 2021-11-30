package com.example.banktransaction.exception;

public class StatusException  extends APIRequestException{

    public StatusException(String message) {
        super(message);
    }

    public StatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
