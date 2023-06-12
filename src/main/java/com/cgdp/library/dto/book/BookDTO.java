package com.cgdp.library.dto.book;

import com.cgdp.library.validation.ValidISBN;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record BookDTO(@NotNull Long id,
                      @NotBlank String title,
                      @NotNull Long authorId,
                      LocalDate publicationYear,
                      @ValidISBN(required = true) String isbn,
                      @NotBlank String genre) {}
