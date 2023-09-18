package com.cgdpd.library.common.client;

import com.cgdpd.library.common.error.ErrorResponse;
import com.cgdpd.library.common.exception.InternalServerHttpException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.function.Function;

@Slf4j
public abstract class InternalHttpClient {

    protected Function<? super Throwable, ? extends Throwable> onErrorMap() {
        return throwable -> {
            if (throwable instanceof WebClientResponseException e) {
                return new InternalServerHttpException(e.getResponseBodyAs(ErrorResponse.class));
            } else if (throwable instanceof WebClientRequestException e) {
                // TODO: 18/09/2023 Handle possible problems here
                // TODO: 18/09/2023 Connection failed, timeout reached etc...
            }
            log.error("Unexpected exception from web client request", throwable);
            return throwable;
        };
    }
}
