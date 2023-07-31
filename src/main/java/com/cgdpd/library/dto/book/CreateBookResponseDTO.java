package com.cgdpd.library.dto.book;

import static com.cgdpd.library.validation.Validator.required;

import com.cgdpd.library.type.BookId;

public record CreateBookResponseDTO(BookId bookId) {

    public CreateBookResponseDTO(BookId bookId) {
        this.bookId = required("bookId", bookId);
    }
}
