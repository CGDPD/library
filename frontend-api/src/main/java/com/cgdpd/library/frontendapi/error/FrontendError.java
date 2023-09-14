package com.cgdpd.library.frontendapi.error;

import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;
import static com.cgdpd.library.common.validation.Validator.requiredValidHttpStatus;

import lombok.Builder;

@Builder
public record FrontendError(Integer httpStatus, String errorMessage) {

    public FrontendError {
        requiredValidHttpStatus(httpStatus);
        requiredNotBlank("errorMessage", errorMessage);
    }
}
