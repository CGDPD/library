package com.cgdpd.library.frontendapi.service;

import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.frontendapi.queries.BookQueries;
import com.cgdpd.library.common.type.Isbn13;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookService {

    private final BookQueries bookQueries;

    public DetailedBookDto getDetailedBookDtoByIsbn13(Isbn13 isbn13) {
        return bookQueries.getDetailedBooks(isbn13).block();
    }
}
