package com.cgdpd.library.dto.book;

import static com.cgdpd.library.validation.Validator.checkYearNotFuture;
import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.requiredNotBlank;
import static com.cgdpd.library.validation.Validator.requiredValidIsbn;

import java.util.Optional;
import com.cgdpd.library.type.AuthorId;
import lombok.Builder;

@Builder
public record CreateBookRequestDTO(String title,
                                   AuthorId authorId,
                                   String isbn,
                                   String genre,
                                   Optional<Short> publicationYear) {

    public CreateBookRequestDTO(String title,
                                AuthorId authorId,
                                String isbn,
                                String genre,
                                Optional<Short> publicationYear) {
        this.title = requiredNotBlank("title", title);
        this.authorId = required("authorId", authorId);
        this.publicationYear = checkYearNotFuture("publicationYear", publicationYear);
        this.isbn = requiredValidIsbn("isbn", isbn);
        this.genre = requiredNotBlank("genre", genre);
    }
}
