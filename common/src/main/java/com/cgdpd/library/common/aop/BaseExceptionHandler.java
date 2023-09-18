package com.cgdpd.library.common.aop;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.cgdpd.library.common.error.ErrorResponse;
import com.cgdpd.library.common.exception.InternalServerHttpException;
import com.cgdpd.library.common.exception.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Clock;

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
    public ResponseEntity<ErrorResponse> handleRequestNotPermitted(NotFoundException exception) {
        return buildResponseEntity(NOT_FOUND, exception.getMessage());
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
