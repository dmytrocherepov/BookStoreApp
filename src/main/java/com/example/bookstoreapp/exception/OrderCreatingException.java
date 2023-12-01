package com.example.bookstoreapp.exception;

public class OrderCreatingException extends RuntimeException {
    public OrderCreatingException(String message) {
        super(message);
    }
}
