package com.cgdp.library.dto.book;

import static com.cgdp.library.validation.ValidationUtility.required;
import static com.cgdp.library.validation.ValidationUtility.requiredNotBlank;
import static com.cgdp.library.validation.ValidationUtility.requiredValidDate;
import static com.cgdp.library.validation.ValidationUtility.requiredValidIsbn;

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
                                   @NotBlank String genre) {

    public CreateBookRequestDTO(String title, @NotNull Long authorId,
                                LocalDate publicationYear, String isbn, String genre) {

        this.title = requiredNotBlank("title", title);
        this.authorId = required("authorId", authorId);
        this.publicationYear = requiredValidDate("publicationYear", publicationYear);
        this.isbn = requiredValidIsbn("isbn", isbn);
        this.genre = requiredNotBlank("genre", genre);
    }
}
