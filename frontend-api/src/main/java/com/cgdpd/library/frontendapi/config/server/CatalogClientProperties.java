package com.cgdpd.library.frontendapi.config.server;

import static com.cgdpd.library.common.validation.Validator.required;
import static com.cgdpd.library.common.validation.Validator.requiredValidUrl;


import com.cgdpd.library.common.http.security.client.InternalHttpClientProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Optional;

@ConfigurationProperties(prefix = "cgdpd.http.client.internal.catalog")
public record CatalogClientProperties(String url,
                                      BasicAuthPropertiesRecord basicAuth,
                                      Optional<Duration> connectionTimeout,
                                      Optional<Duration> readTimeout)
      implements InternalHttpClientProperties {

    public CatalogClientProperties {
        requiredValidUrl(url);
        required("basicAuth", basicAuth);
    }
}
