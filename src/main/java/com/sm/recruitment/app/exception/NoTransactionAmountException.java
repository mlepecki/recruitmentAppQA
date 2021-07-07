package com.sm.recruitment.app.exception;

public class NoTransactionAmountException extends RuntimeException {

    public NoTransactionAmountException(String message) {
        super(message);
    }

}
