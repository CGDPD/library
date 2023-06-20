package com.cgdp.library.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import org.hibernate.validator.constraints.ISBN;

@Builder
public record CreateBookRequestDTO(@NotBlank String title,
                                   @NotNull Long authorId,
                                   LocalDate publicationYear,
                                   @ISBN String isbn,
                                   @NotBlank String genre) {}
