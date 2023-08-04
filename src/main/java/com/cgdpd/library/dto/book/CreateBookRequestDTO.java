package com.cgdpd.library.dto.book;

import static com.cgdpd.library.validation.Validator.checkYearNotFuture;
import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.requiredNotBlank;

import com.cgdpd.library.type.AuthorId;
import com.cgdpd.library.type.Isbn13;
import java.util.Optional;
import lombok.Builder;

@Builder
public record CreateBookRequestDTO(String title,
                                   AuthorId authorId,
                                   Isbn13 isbn,
                                   String genre,
                                   Optional<Short> publicationYear) {

    public CreateBookRequestDTO(String title,
                                AuthorId authorId,
                                Isbn13 isbn,
                                String genre,
                                Optional<Short> publicationYear) {
        this.title = requiredNotBlank("title", title);
        this.authorId = required("authorId", authorId);
        this.publicationYear = checkYearNotFuture("publicationYear", publicationYear);
        this.isbn = required("isbn", isbn);
        this.genre = requiredNotBlank("genre", genre);
    }
}
