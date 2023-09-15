package com.cgdpd.library.frontendapi.aop;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

import com.cgdpd.library.common.exception.NotFoundException;
import com.cgdpd.library.frontendapi.error.FrontendError;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    // TODO: 11/09/2023 This exceptions are for frontend so they should be refactored so we don't leak service status

    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<FrontendError> handleCallNotPermittedException() {
        return buildResponseEntity(SERVICE_UNAVAILABLE, "Open circuit breaker");
    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<FrontendError> handleRequestNotPermitted() {
        return buildResponseEntity(TOO_MANY_REQUESTS, "RPM limit reached");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<FrontendError> handleRequestNotPermitted(NotFoundException exception) {
        return buildResponseEntity(NOT_FOUND, exception.getMessage());
    }

    public ResponseEntity<FrontendError> buildResponseEntity(HttpStatus httpStatus,
                                                             String errorMessage) {
        return ResponseEntity
              .status(httpStatus)
              .body(buildBody(httpStatus, errorMessage));
    }

    public FrontendError buildBody(HttpStatus httpStatus, String errorMessage) {
        return FrontendError.builder()
              .httpStatus(httpStatus.value())
              .errorMessage(errorMessage)
              .build();
    }
}
