package com.cgdpd.library.frontendapi.config;


import com.cgdpd.library.common.http.security.client.WebClientProvider;
import com.cgdpd.library.frontendapi.config.server.CatalogClientProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HttpClientConfig {

    @Bean("catalogWebClient")
    public WebClient catalogWebClient(CatalogClientProperties properties) {
        return WebClientProvider.webClientWithBasicAuth(properties);
    }
}
