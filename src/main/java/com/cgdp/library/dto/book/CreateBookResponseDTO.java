package com.cgdp.library.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class CreateBookResponseDTO {

    @NotNull
    private Long id;
    @NotBlank
    private String title;
    @NotNull
    private Long authorId;
    @NotNull
    private LocalDate publicationYear;
    @NotBlank
    private String isbn;
    @NotBlank
    private String genre;
}
