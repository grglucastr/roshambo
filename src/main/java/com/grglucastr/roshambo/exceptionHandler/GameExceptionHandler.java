package com.grglucastr.roshambo.exceptionHandler;

import com.grglucastr.roshambo.exceptions.EmptyStrategiesException;
import com.grglucastr.roshambo.exceptions.NotEnoughMovesException;
import com.grglucastr.roshambo.exceptions.NotEnoughPlayersException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GameExceptionHandler {

    @ExceptionHandler( value = NotEnoughPlayersException.class)
    public ResponseEntity<Object> handleNotEnoughPlayersException(NotEnoughPlayersException e){
        CustomExceptionPayload exception = new CustomExceptionPayload(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(exception, exception.getHttpStatus());
    };

    @ExceptionHandler( value = EmptyStrategiesException.class)
    public ResponseEntity<Object> handleEmptyStrategies(EmptyStrategiesException e){
        CustomExceptionPayload exception = new CustomExceptionPayload(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(exception, exception.getHttpStatus());
    };

    @ExceptionHandler( value = NotEnoughMovesException.class)
    public ResponseEntity<Object> handleNotEnoughMovesException(NotEnoughMovesException e){
        CustomExceptionPayload exception = new CustomExceptionPayload(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(exception, exception.getHttpStatus());
    };
}
