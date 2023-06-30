package com.janonimo.tazma.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiException {
    
    private final String message;
    
    private final HttpStatus httpStatus;
   
    private final ZonedDateTime timeStamp;
    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timeStamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }
}
