package com.cgdpd.library.catalog.app.aop;

import com.cgdpd.library.common.aop.BaseExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;

@RestControllerAdvice
public class ApiExceptionHandler extends BaseExceptionHandler {

    protected ApiExceptionHandler(Clock clock) {
        super(clock);
    }
}
