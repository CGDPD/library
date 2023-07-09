package com.cgdpd.library.dto.book;

import static com.cgdpd.library.validation.Validator.checkBeforeNow;
import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.requiredNotBlank;
import static com.cgdpd.library.validation.Validator.requiredValidIsbn;

import java.time.LocalDate;
import java.util.Optional;
import lombok.Builder;

@Builder
public record CreateBookRequestDTO(String title,
                                   Long authorId,
                                   String isbn,
                                   String genre,
                                   Optional<LocalDate> publicationYear) {

    public CreateBookRequestDTO(String title,
                                Long authorId,
                                String isbn,
                                String genre,
                                Optional<LocalDate> publicationYear) {
        this.title = requiredNotBlank("title", title);
        this.authorId = required("authorId", authorId);
        this.publicationYear = checkBeforeNow("publicationYear", publicationYear);
        this.isbn = requiredValidIsbn("isbn", isbn);
        this.genre = requiredNotBlank("genre", genre);
    }
}
