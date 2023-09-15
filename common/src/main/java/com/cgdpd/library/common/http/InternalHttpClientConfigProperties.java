package com.cgdpd.library.common.http;

import java.time.Duration;
import java.util.Optional;

public interface InternalHttpClientConfigProperties {

    String url();

    Optional<Duration> connectionTimeout();

    Optional<Duration> readTimeout();

    default Duration defaultConnectionTimeout() {
        return Duration.ofSeconds(10);
    }

    default Duration defaultReadTimeout() {
        return Duration.ofMillis(500);
    }
}
