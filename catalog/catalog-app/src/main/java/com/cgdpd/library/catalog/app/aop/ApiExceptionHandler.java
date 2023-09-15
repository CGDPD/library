package com.cgdpd.library.catalog.app.aop;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.cgdpd.library.common.exception.NotFoundException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public void handleNotFoundException() {
    }
}
