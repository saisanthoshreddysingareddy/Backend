package com.example.bmsbookticket.exceptions;

public class RatingOutOfRangeException extends RuntimeException {
    public RatingOutOfRangeException(String message) {
        super(message);
    }
}
