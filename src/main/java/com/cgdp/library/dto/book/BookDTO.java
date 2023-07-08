package com.cgdp.library.dto.book;

import static com.cgdp.library.validation.Validator.required;
import static com.cgdp.library.validation.Validator.requiredBeforeNow;
import static com.cgdp.library.validation.Validator.requiredNotBlank;
import static com.cgdp.library.validation.Validator.requiredValidIsbn;

import java.time.LocalDate;
import java.util.Optional;
import lombok.Builder;

@Builder
public record BookDTO(Long id,
                      String title,
                      Long authorId,
                      Optional<LocalDate> publicationYear,
                      String isbn,
                      String genre) {

    public BookDTO(Long id, String title, Long authorId,
                   LocalDate publicationYear, String isbn, String genre) {
        this.id = required("id", id);
        this.title = requiredNotBlank("title", title);
        this.authorId = required("authorId", authorId);
        this.publicationYear = Optional.ofNullable(
              requiredBeforeNow("publicationYear", publicationYear));
        this.isbn = requiredValidIsbn("isbn", isbn);
        this.genre = requiredNotBlank("genre", genre);
    }
}
