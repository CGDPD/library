package com.cgdpd.library.frontendapi.controller;

import com.cgdpd.library.frontendapi.dto.BookViewDto;
import com.cgdpd.library.frontendapi.mapper.BookMapper;
import com.cgdpd.library.frontendapi.service.BookService;
import com.cgdpd.library.common.type.Isbn13;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/view")
public class ViewController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping("/book/{isbn13}")
    public BookViewDto detailedBookDto(@PathVariable Isbn13 isbn13) {
        return bookMapper.mapToBookView(bookService.getDetailedBookDtoByIsbn13(isbn13));
    }
}
