package com.cgdpd.library.frontendapi.aop;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.cgdpd.library.common.aop.BaseExceptionHandler;
import com.cgdpd.library.common.error.ErrorResponse;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Clock;

@ControllerAdvice
public class ApiExceptionHandler extends BaseExceptionHandler {

    public ApiExceptionHandler(Clock clock) {
        super(clock);
    }

    @ExceptionHandler({RequestNotPermitted.class, CallNotPermittedException.class})
    public ResponseEntity<ErrorResponse> handleResilienceErrors() {
        return buildResponseEntity(INTERNAL_SERVER_ERROR,
              "The server is currently experiencing issues. Please try again later.");
    }
}
