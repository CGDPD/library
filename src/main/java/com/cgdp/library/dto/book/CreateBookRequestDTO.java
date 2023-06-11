package com.cgdp.library.dto.book;

import com.cgdp.library.customValidator.ISBNConstraint;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;


public record CreateBookRequestDTO(@NotBlank String title, @NotNull Long authorId, @Nullable LocalDate publicationYear, @Nullable @ISBNConstraint String isbn, @NotBlank String genre) {}
