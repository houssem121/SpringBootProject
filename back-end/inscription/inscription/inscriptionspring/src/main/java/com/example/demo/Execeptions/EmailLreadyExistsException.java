package com.example.demo.Execeptions;

public class EmailLreadyExistsException extends RuntimeException {
    public EmailLreadyExistsException(String message) {
        super(message);
    }
    public EmailLreadyExistsException() {
        super("Email already exists");
    }
}
