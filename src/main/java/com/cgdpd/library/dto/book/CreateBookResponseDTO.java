package com.cgdpd.library.dto.book;

import static com.cgdpd.library.validation.Validator.required;

public record CreateBookResponseDTO(Long bookId) {

    public CreateBookResponseDTO(Long bookId) {
        this.bookId = required("bookId", bookId);
    }
}
