package com.cgdpd.library.controller;

import com.cgdpd.library.dto.book.CreateBookRequestDTO;
import com.cgdpd.library.dto.book.CreateBookResponseDTO;
import com.cgdpd.library.dto.book.DetailedBookDTO;
import com.cgdpd.library.dto.book.SearchBookCriteria;
import com.cgdpd.library.dto.pagination.PagedResponse;
import com.cgdpd.library.dto.pagination.PaginationCriteria;
import com.cgdpd.library.service.BookService;
import com.cgdpd.library.type.Isbn13;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@AllArgsConstructor
@RequestMapping("/books")
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBookResponseDTO createBook(@RequestBody CreateBookRequestDTO requestDTO) {
        var book = bookService.createBook(requestDTO);
        return new CreateBookResponseDTO(book.id());
    }

    @GetMapping("/isbn13/{isbn13}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedBookDTO getBook(@PathVariable Isbn13 isbn13) {
        return bookService.getDetailedBookByIsbn13(isbn13);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<DetailedBookDTO> searchBook(
          @RequestParam(required = false) Optional<SearchBookCriteria> searchBookCriteria,
          PaginationCriteria paginationCriteria) {
        return bookService.findDetailedBooksPage(paginationCriteria,
              searchBookCriteria.orElse(null));
    }
}
