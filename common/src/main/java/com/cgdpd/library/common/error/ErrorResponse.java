package com.cgdpd.library.common.error;

import static com.cgdpd.library.common.validation.Validator.required;
import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;
import static com.cgdpd.library.common.validation.Validator.requiredValidHttpStatus;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ErrorResponse(Integer status,
                            String error,
                            String message,
                            Instant timestamp) {

    public ErrorResponse {
        requiredValidHttpStatus(status);
        requiredNotBlank("error", error);
        requiredNotBlank("message", message);
        required("timestamp", timestamp);
    }
}
