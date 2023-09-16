package com.cgdpd.library.frontendapi.configuration.client;

import static com.cgdpd.library.common.validation.Validator.requiredValidUrl;

import com.cgdpd.library.common.http.InternalHttpClientConfigProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Optional;

@ConfigurationProperties(prefix = "cgdpd.http.client.internal.catalog")
public record CatalogClientProperties(String url,
                                      Optional<Duration> connectionTimeout,
                                      Optional<Duration> readTimeout)
      implements InternalHttpClientConfigProperties {
    public CatalogClientProperties {
        requiredValidUrl("url", url);
    }
}
