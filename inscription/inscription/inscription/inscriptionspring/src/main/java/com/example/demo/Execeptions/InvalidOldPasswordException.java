package com.example.demo.Execeptions;

public class InvalidOldPasswordException extends RuntimeException {
    public InvalidOldPasswordException() {
        super("Invalid old password provided.");
    }

    public InvalidOldPasswordException(String message) {
        super(message);
    }
}