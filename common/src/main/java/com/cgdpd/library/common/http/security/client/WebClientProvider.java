package com.cgdpd.library.common.http.security.client;

import io.netty.channel.ChannelOption;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

public class WebClientProvider {

    public static WebClient webClientWithBasicAuth(InternalHttpClientProperties properties) {
        var httpClient = HttpClient.create()
              .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
                    (int) properties.connectionTimeout()
                          .orElseGet(properties::defaultConnectionTimeout)
                          .toMillis())
              .responseTimeout(properties.readTimeout()
                    .orElseGet(properties::defaultReadTimeout));

        return WebClient.builder()
              .baseUrl(properties.url())
              .defaultHeaders(header -> {
                  var basicAuth = properties.basicAuth();
                  header.setBasicAuth(basicAuth.username(), basicAuth.password().value());
              })
              .clientConnector(new ReactorClientHttpConnector(httpClient))
              .build();
    }
}
