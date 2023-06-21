package com.cgdp.library.dto.book;

import static com.cgdp.library.validation.ValidationUtility.required;

import jakarta.validation.constraints.NotNull;

public record CreateBookResponseDTO(@NotNull Long bookId) {

    public CreateBookResponseDTO(@NotNull Long bookId) {

        this.bookId = required("bookId", bookId);
    }
}
