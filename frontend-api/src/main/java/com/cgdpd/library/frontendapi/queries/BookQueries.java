package com.cgdpd.library.frontendapi.queries;

import com.cgdpd.library.catalog.client.LibraryCatalogClient;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.dto.SearchBookCriteria;
import com.cgdpd.library.common.pagination.PagedResponse;
import com.cgdpd.library.common.pagination.PaginationCriteria;
import com.cgdpd.library.common.type.Isbn13;
import com.cgdpd.library.frontendapi.dto.BookViewDto;
import com.cgdpd.library.frontendapi.mapper.BookMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@CircuitBreaker(name = "CatalogCircuitBreaker")
@RateLimiter(name = "CatalogRateLimiter")
@AllArgsConstructor
@Component
public class BookQueries {

    private final LibraryCatalogClient libraryCatalogClient;
    private final BookMapper bookMapper;

    public Mono<DetailedBookDto> getDetailedBooks(Isbn13 isbn13) {
        return libraryCatalogClient.getDetailedBookDto(isbn13);
    }

    public Mono<PagedResponse<BookViewDto>> getBooksByCriteria(
          PaginationCriteria paginationCriteria, SearchBookCriteria searchBookCriteria) {
        return libraryCatalogClient.getBooksByCriteria(paginationCriteria, searchBookCriteria)
              .map(detailedBookDtos -> detailedBookDtos.content().stream()
                    .map(bookMapper::mapToBookView)
                    .collect(Collectors.toList()))
              .map(bookViewDtos -> {
                  var totalElements = bookViewDtos.size();
                  var totalPages = (totalElements + paginationCriteria.pageSize() - 1)
                        / paginationCriteria.pageSize();
                  return PagedResponse.<BookViewDto>builder()
                        .content(bookViewDtos)
                        .pageNumber(paginationCriteria.pageIndex())
                        .pageSize(paginationCriteria.pageSize())
                        .totalElements(totalElements)
                        .totalPages(totalPages)
                        .build();
              });
    }
}
