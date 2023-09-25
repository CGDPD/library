package com.cgdpd.library.frontendapi.queries;

import com.cgdpd.library.catalog.client.LibraryCatalogClient;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.dto.SearchBookCriteria;
import com.cgdpd.library.common.pagination.PaginationCriteria;
import com.cgdpd.library.common.type.Isbn13;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CircuitBreaker(name = "CatalogCircuitBreaker")
@RateLimiter(name = "CatalogRateLimiter")
@AllArgsConstructor
@Component
public class BookQueries {

    private final LibraryCatalogClient libraryCatalogClient;

    public Mono<DetailedBookDto> getDetailedBooks(Isbn13 isbn13) {
        return libraryCatalogClient.getDetailedBookDto(isbn13);
    }

    public Flux<DetailedBookDto> searchBooks(PaginationCriteria paginationCriteria,
                                             SearchBookCriteria searchBookCriteria) {
        return libraryCatalogClient.searchBooks(paginationCriteria, searchBookCriteria);
    }
}
