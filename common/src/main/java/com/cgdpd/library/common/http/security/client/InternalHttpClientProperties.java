package com.cgdpd.library.common.http.security.client;

import java.time.Duration;
import java.util.Optional;

public interface InternalHttpClientProperties {

    String url();

    BasicAuthProperties basicAuth();

    Optional<Duration> connectionTimeout();

    Optional<Duration> readTimeout();

    default Duration defaultConnectionTimeout() {
        return Duration.ofSeconds(10);
    }

    default Duration defaultReadTimeout() {
        return Duration.ofMillis(500);
    }
}
