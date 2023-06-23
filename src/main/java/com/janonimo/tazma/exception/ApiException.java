package com.janonimo.tazma.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    @Getter
    @Setter
    private final String message;
    @Getter
    @Setter
    private final HttpStatus httpStatus;
    @Getter
    @Setter
    private final ZonedDateTime timeStamp;
    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timeStamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }
}
