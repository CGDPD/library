package com.cgdpd.library.common.http.security.client;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

public class WebClientProvider {

    public static WebClient webClientWithBasicAuth(InternalHttpClientProperties properties) {
        // TODO: 17/09/2023 connection timeout
        var httpClient = HttpClient.create()
              .responseTimeout(properties.readTimeout()
                    .orElseGet(properties::defaultReadTimeout));

        return WebClient.builder()
              .baseUrl(properties.url())
              .defaultHeaders(header -> {
                  var basicAuth = properties.basicAuth();
                  header.setBasicAuth(basicAuth.username(), basicAuth.password());
              })
              .clientConnector(new ReactorClientHttpConnector(httpClient))
              .build();
    }
}
