package com.example.banktransaction.exception;

public class AuthorityException extends APIRequestException{
    public AuthorityException(String message) {
        super(message);
    }

    public AuthorityException(String message, Throwable cause) {
        super(message, cause);
    }
}
