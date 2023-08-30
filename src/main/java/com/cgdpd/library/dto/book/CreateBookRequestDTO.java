package com.cgdpd.library.dto.book;

import static com.cgdpd.library.util.OptionalUtil.actualOrEmpty;
import static com.cgdpd.library.validation.Validator.checkYearNotFuture;
import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.requiredNotBlank;

import com.cgdpd.library.type.AuthorId;
import com.cgdpd.library.type.Isbn13;

import lombok.Builder;

import java.util.Optional;

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
        this.isbn = required("isbn", isbn);
        this.genre = requiredNotBlank("genre", genre);
        this.publicationYear = checkYearNotFuture("publicationYear",
              actualOrEmpty(publicationYear));
    }
}
