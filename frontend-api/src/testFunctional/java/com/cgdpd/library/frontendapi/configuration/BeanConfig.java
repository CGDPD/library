package com.cgdpd.library.frontendapi.configuration;

import com.cgdpd.library.catalog.client.stub.LibraryCatalogStubClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * It needs to have the same name as the one in production code
 * to make sure it uses this bean instead of "real" one
 */
@Configuration
public class BeanConfig {

    @Bean
    public LibraryCatalogStubClient libraryCatalogClient() {
        return new LibraryCatalogStubClient();
    }
}
