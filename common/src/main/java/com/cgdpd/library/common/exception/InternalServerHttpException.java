package com.cgdpd.library.common.exception;

import static com.cgdpd.library.common.validation.Validator.required;

import com.cgdpd.library.common.error.ErrorResponse;


public class InternalServerHttpException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public InternalServerHttpException(ErrorResponse errorResponse) {
        this.errorResponse = required("errorResponse", errorResponse);
    }

    public ErrorResponse errorResponse() {
        return errorResponse;
    }
}
