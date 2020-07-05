package com.grglucastr.roshambo.exceptions;

public class NotEnoughPlayersException extends RuntimeException {

    public NotEnoughPlayersException(String message) {
        super(message);
    }
}
