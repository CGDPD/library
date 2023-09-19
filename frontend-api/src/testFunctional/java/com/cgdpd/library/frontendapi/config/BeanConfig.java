package com.cgdpd.library.frontendapi.config;

import com.cgdpd.library.catalog.client.stub.CatalogInMemoryDb;
import com.cgdpd.library.catalog.client.stub.LibraryCatalogStubClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * It needs to have the same name as the one in production code
 * to make sure it uses this bean instead of "real" one
 */
@Configuration
public class BeanConfig {

    @Bean
    public LibraryCatalogStubClient libraryCatalogClient(CatalogInMemoryDb inMemoryDb) {
        return new LibraryCatalogStubClient(inMemoryDb);
    }

    @Bean
    public CatalogInMemoryDb catalogInMemoryDatabase() {
        return new CatalogInMemoryDb();
    }

    @Bean
    public Clock clock() {
        var fixedInstant = LocalDateTime.of(2023, 9, 17, 13, 25, 0)
              .toInstant(ZoneOffset.UTC);
        return Clock.fixed(fixedInstant, ZoneOffset.UTC);
    }
}
