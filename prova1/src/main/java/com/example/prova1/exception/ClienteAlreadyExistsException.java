package com.example.prova1.exception;

public class ClienteAlreadyExistsException extends RuntimeException {
    public ClienteAlreadyExistsException(String message) {
        super(message);
    }
}
