package com.cgdpd.library.frontendapi.queries;

import com.cgdpd.library.catalog.client.LibraryCatalogReactiveClient;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.types.Isbn13;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@CircuitBreaker(name = "CatalogCircuitBreaker")
@RateLimiter(name = "CatalogRateLimiter")
@AllArgsConstructor
@Component
public class BookQueries {

    private final LibraryCatalogReactiveClient libraryCatalogReactiveClient;

    public Mono<DetailedBookDto> getDetailedBooks(Isbn13 isbn13) {
        return libraryCatalogReactiveClient.getDetailedBookDto(isbn13);
    }
}
