package com.cgdp.library.controller;

import com.cgdp.library.dto.book.BookDTO;
import com.cgdp.library.dto.book.CreateBookRequestDTO;
import com.cgdp.library.dto.book.CreateBookResponseDTO;
import com.cgdp.library.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/books")
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBookResponseDTO createBook(@Valid @RequestBody CreateBookRequestDTO requestDTO) {
        BookDTO bookDTO = bookService.createBook(requestDTO);
        return new CreateBookResponseDTO(bookDTO.id());
    }
}
