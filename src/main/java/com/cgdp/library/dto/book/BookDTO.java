package com.cgdp.library.dto.book;

import static com.cgdp.library.validation.Validator.required;
import static com.cgdp.library.validation.Validator.requiredNotNullOrBlank;
import static com.cgdp.library.validation.Validator.requiredBeforeNow;
import static com.cgdp.library.validation.Validator.requiredValidIsbn;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record BookDTO(Long id,
                      String title,
                      Long authorId,
                      LocalDate publicationYear,
                      String isbn,
                      String genre) {

    public BookDTO(Long id, String title, Long authorId,
                   LocalDate publicationYear, String isbn, String genre) {
        this.id = required("id", id);
        this.title = requiredNotNullOrBlank("title", title);
        this.authorId = required("authorId", authorId);
        this.publicationYear = requiredBeforeNow("publicationYear", publicationYear);
        this.isbn = requiredValidIsbn("isbn", isbn);
        this.genre = requiredNotNullOrBlank("genre", genre);
    }
}
