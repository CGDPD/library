package com.cgdpd.library.common.aop;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import com.cgdpd.library.common.error.ErrorResponse;
import com.cgdpd.library.common.exception.InternalServerHttpException;
import com.cgdpd.library.common.exception.NotFoundException;
import com.cgdpd.library.common.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Clock;

@Slf4j
public abstract class BaseExceptionHandler {

    private final Clock clock;

    protected BaseExceptionHandler(Clock clock) {
        this.clock = clock;
    }

    @ExceptionHandler(InternalServerHttpException.class)
    public ResponseEntity<ErrorResponse> handleServerException(InternalServerHttpException e) {
        return ResponseEntity
              .status(e.errorResponse().status())
              .body(e.errorResponse());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRequestNotPermitted(NotFoundException e) {
        return buildResponseEntity(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotAcceptableException(
          HttpMediaTypeNotSupportedException e) {
        return buildResponseEntity(UNSUPPORTED_MEDIA_TYPE, e.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotAcceptableException(
          HttpMediaTypeNotAcceptableException e) {
        return buildResponseEntity(NOT_ACCEPTABLE, e.getMessage());
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageConversionException(
          HttpMessageConversionException e) {
        return buildResponseEntity(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException e) {
        log.error("Validation exception was during the execution of the program", e);
        return buildResponseEntity(INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(String.format("Unexpected exception class, %s", e.getClass()), e);
        return buildResponseEntity(INTERNAL_SERVER_ERROR,
              "The service encountered an unexpected condition which prevented it from fulfilling the request");
    }

    protected ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus httpStatus,
                                                                String errorMessage) {
        return ResponseEntity
              .status(httpStatus)
              .body(buildBody(httpStatus, errorMessage));
    }

    protected ErrorResponse buildBody(HttpStatus httpStatus, String errorMessage) {
        return ErrorResponse.builder()
              .status(httpStatus.value())
              .error(httpStatus.getReasonPhrase())
              .message(errorMessage)
              .timestamp(clock.instant())
              .build();
    }
}
