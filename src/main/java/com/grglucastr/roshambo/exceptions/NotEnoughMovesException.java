package com.grglucastr.roshambo.exceptions;

public class NotEnoughMovesException extends RuntimeException {
    public NotEnoughMovesException(String message) {
        super(message);
    }
}
