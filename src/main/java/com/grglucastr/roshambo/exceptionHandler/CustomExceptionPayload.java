package com.grglucastr.roshambo.exceptionHandler;

import org.springframework.http.HttpStatus;

public class CustomExceptionPayload {

    private final String message;
    private final HttpStatus httpStatus;

    public CustomExceptionPayload(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
