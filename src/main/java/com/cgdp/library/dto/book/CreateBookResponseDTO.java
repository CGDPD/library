package com.cgdp.library.dto.book;

import static com.cgdp.library.validation.Validator.required;

public record CreateBookResponseDTO(Long bookId) {

    public CreateBookResponseDTO(Long bookId) {
        this.bookId = required("bookId", bookId);
    }
}
