package com.cgdp.library.dto.book;

import static com.cgdp.library.validation.Validator.required;
import static com.cgdp.library.validation.Validator.requiredNotBlank;
import static com.cgdp.library.validation.Validator.requiredValidDate;
import static com.cgdp.library.validation.Validator.requiredValidIsbn;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record CreateBookRequestDTO(String title,
                                   Long authorId,
                                   LocalDate publicationYear,
                                   String isbn,
                                   String genre) {

    public CreateBookRequestDTO(String title, Long authorId,
                                LocalDate publicationYear, String isbn, String genre) {
        this.title = requiredNotBlank("title", title);
        this.authorId = required("authorId", authorId);
        this.publicationYear = requiredValidDate("publicationYear", publicationYear);
        this.isbn = requiredValidIsbn("isbn", isbn);
        this.genre = requiredNotBlank("genre", genre);
    }
}
