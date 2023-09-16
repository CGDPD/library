package com.cgdpd.library.frontendapi.config;

import com.cgdpd.library.common.http.InternalHttpClientConfigProperties;
import com.cgdpd.library.frontendapi.config.client.CatalogClientProperties;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class HttpClientConfig {

    @Bean("catalogRestTemplate")
    public RestTemplate catalogRestTemplate(CatalogClientProperties properties) {
        return buildRestClient(properties);
    }

    private RestTemplate buildRestClient(InternalHttpClientConfigProperties properties) {
        return new RestTemplateBuilder()
              .uriTemplateHandler(new DefaultUriBuilderFactory(properties.url()))
              .setConnectTimeout(properties.connectionTimeout()
                    .orElse(properties.defaultConnectionTimeout()))
              .setReadTimeout(properties.readTimeout()
                    .orElse(properties.defaultReadTimeout()))
              .build();
    }
}
