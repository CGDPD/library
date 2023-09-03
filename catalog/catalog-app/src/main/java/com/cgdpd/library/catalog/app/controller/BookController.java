package com.cgdpd.library.catalog.app.controller;

import com.cgdpd.library.catalog.app.service.BookService;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDTO;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDTO;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.types.Isbn13;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/book")
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody CreateBookRequestDTO requestDTO) {
        return bookService.createBook(requestDTO);
    }

    @GetMapping("/isbn13/{isbn13}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedBookDTO getBook(@PathVariable Isbn13 isbn13) {
        return bookService.getDetailedBookByIsbn13(isbn13);
    }
}
