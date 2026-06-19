package com.example.qcommerce.exceptions;

public class TaskMissingException extends RuntimeException {
    public TaskMissingException(String message) {
        super(message);
    }
}
