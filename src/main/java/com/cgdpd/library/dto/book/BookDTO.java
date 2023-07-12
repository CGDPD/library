package com.cgdpd.library.dto.book;

import static com.cgdpd.library.validation.Validator.checkYearNotFuture;
import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.requiredNotBlank;
import static com.cgdpd.library.validation.Validator.requiredValidIsbn;

import java.util.Optional;
import lombok.Builder;

@Builder
public record BookDTO(Long id,
                      String title,
                      Long authorId,
                      String isbn,
                      String genre,
                      Optional<Short> publicationYear) {

    public BookDTO(Long id,
                   String title,
                   Long authorId,
                   String isbn,
                   String genre,
                   Optional<Short> publicationYear) {
        this.id = required("id", id);
        this.title = requiredNotBlank("title", title);
        this.authorId = required("authorId", authorId);
        this.isbn = requiredValidIsbn("isbn", isbn);
        this.genre = requiredNotBlank("genre", genre);
        this.publicationYear = checkYearNotFuture("publicationYear", publicationYear);
    }
}
