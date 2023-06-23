package com.janonimo.tazma.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
       return new ResponseEntity<>(new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))), HttpStatus.BAD_REQUEST
       );

    }
}
