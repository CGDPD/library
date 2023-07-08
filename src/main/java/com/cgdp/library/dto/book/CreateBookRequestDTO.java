package com.cgdp.library.dto.book;

import static com.cgdp.library.validation.Validator.checkBeforeNow;
import static com.cgdp.library.validation.Validator.required;
import static com.cgdp.library.validation.Validator.requiredNotBlank;
import static com.cgdp.library.validation.Validator.requiredValidIsbn;

import java.time.LocalDate;
import java.util.Optional;
import lombok.Builder;

@Builder
public record CreateBookRequestDTO(String title,
                                   Long authorId,
                                   Optional<LocalDate> publicationYear,
                                   String isbn,
                                   String genre) {

    public CreateBookRequestDTO(String title, Long authorId,
                                Optional<LocalDate> publicationYear, String isbn, String genre) {
        this.title = requiredNotBlank("title", title);
        this.authorId = required("authorId", authorId);
        this.publicationYear = checkBeforeNow("publicationYear", publicationYear);
        this.isbn = requiredValidIsbn("isbn", isbn);
        this.genre = requiredNotBlank("genre", genre);
    }
}
