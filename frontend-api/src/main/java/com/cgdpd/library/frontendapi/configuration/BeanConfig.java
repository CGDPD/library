package com.cgdpd.library.frontendapi.configuration;

import com.cgdpd.library.catalog.client.LibraryCatalogClient;
import com.cgdpd.library.catalog.client.rest.LibraryCatalogHttpClient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

    @Bean
    public LibraryCatalogClient libraryCatalogClient(
          @Qualifier("catalogRestTemplate") RestTemplate catalogRestTemplate) {
        return new LibraryCatalogHttpClient(catalogRestTemplate);
    }
}
