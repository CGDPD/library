package com.cgdpd.library.catalog.app.aop;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.cgdpd.library.common.exception.NotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> resourceNotFoundException(final NotFoundException e) {
        // TODO: 2023-09-03 improve message
        return new ResponseEntity<>("Resource not found", NOT_FOUND);
    }
}
