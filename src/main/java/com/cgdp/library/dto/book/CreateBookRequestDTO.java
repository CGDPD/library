package com.cgdp.library.dto.book;

import com.cgdp.library.entity.AuthorEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateBookRequestDTO {

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
