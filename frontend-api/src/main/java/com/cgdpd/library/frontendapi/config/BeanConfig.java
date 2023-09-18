package com.cgdpd.library.frontendapi.config;

import static com.cgdpd.library.common.provider.ClockProvider.utcClock;

import com.cgdpd.library.catalog.client.LibraryCatalogReactiveClient;
import com.cgdpd.library.catalog.client.rest.LibraryCatalogHttpReactiveClient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Clock;

@Configuration
public class BeanConfig {

    @Bean
    public LibraryCatalogReactiveClient libraryCatalogClient(
          @Qualifier("catalogWebClient") WebClient catalogWebClient) {
        return new LibraryCatalogHttpReactiveClient(catalogWebClient);
    }

    @Bean
    public Clock clock() {
        return utcClock();
    }
}
