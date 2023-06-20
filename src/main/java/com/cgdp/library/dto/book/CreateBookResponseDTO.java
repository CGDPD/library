package com.cgdp.library.dto.book;

import com.cgdp.library.validation.ValidationUtility;
import jakarta.validation.constraints.NotNull;

public record CreateBookResponseDTO(@NotNull Long bookId) {

    public CreateBookResponseDTO(@NotNull Long bookId) {
        this.bookId = ValidationUtility.required("bookId", bookId);
    }
}
