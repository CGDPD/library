package com.cgdpd.library.catalog.app.controller;

import com.cgdpd.library.catalog.app.service.BookService;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.dto.SearchBookCriteria;
import com.cgdpd.library.common.pagination.PagedResponse;
import com.cgdpd.library.common.pagination.PaginationCriteria;
import com.cgdpd.library.common.type.Isbn13;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/{isbn13}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedBookDto getBook(@PathVariable Isbn13 isbn13) {
        return bookService.getDetailedBookByIsbn13(isbn13);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<DetailedBookDto> searchBook(
          PaginationCriteria paginationCriteria,
          Optional<SearchBookCriteria> searchBookCriteria) {
        return bookService.findDetailedBooksPage(paginationCriteria,
              searchBookCriteria.orElseGet(SearchBookCriteria::empty));
    }
}
