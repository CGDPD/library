package com.cgdpd.library.frontendapi.service;

import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.dto.SearchBookCriteria;
import com.cgdpd.library.common.pagination.PaginationCriteria;
import com.cgdpd.library.common.type.Isbn13;
import com.cgdpd.library.frontendapi.queries.BookQueries;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@Service
public class BookService {

    private final BookQueries bookQueries;

    public DetailedBookDto getDetailedBookDtoByIsbn13(Isbn13 isbn13) {
        return bookQueries.getDetailedBooks(isbn13).blockOptional().orElseThrow();
    }

    public Flux<DetailedBookDto> searchBooks(PaginationCriteria paginationCriteria,
                                             SearchBookCriteria searchBookCriteria) {
        return bookQueries.searchBooks(paginationCriteria, searchBookCriteria);
    }
}
