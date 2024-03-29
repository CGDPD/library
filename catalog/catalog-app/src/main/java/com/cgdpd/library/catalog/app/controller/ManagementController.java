package com.cgdpd.library.catalog.app.controller;

import com.cgdpd.library.catalog.app.service.AuthorService;
import com.cgdpd.library.catalog.app.service.BookService;
import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDto;
import com.cgdpd.library.catalog.domain.book.model.Book;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/management")
public class ManagementController {

    private final AuthorService authorService;
    private final BookService bookService;

    @PostMapping("/author")
    @ResponseStatus(HttpStatus.CREATED)
    public Author createAuthor(@RequestBody CreateAuthorRequestDto createAuthorRequestDto) {
        var authorName = createAuthorRequestDto.authorName();
        return authorService.createAuthor(authorName);
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody CreateBookRequestDto requestDTO) {
        return bookService.createBook(requestDTO);
    }
}
