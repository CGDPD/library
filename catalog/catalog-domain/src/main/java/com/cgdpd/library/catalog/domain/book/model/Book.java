package com.cgdpd.library.catalog.domain.book.model;

import static com.cgdpd.library.common.util.OptionalUtil.actualOrEmpty;
import static com.cgdpd.library.common.validation.Validator.checkYearNotFuture;
import static com.cgdpd.library.common.validation.Validator.required;
import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;

import com.cgdpd.library.catalog.domain.author.AuthorId;
import com.cgdpd.library.common.type.Isbn13;

import lombok.Builder;

import java.util.Optional;

@Builder
public record Book(BookId id,
                   String title,
                   AuthorId authorId,
                   Isbn13 isbn,
                   String genre,
                   Optional<Short> publicationYear) {

    public Book(BookId id,
                String title,
                AuthorId authorId,
                Isbn13 isbn,
                String genre,
                Optional<Short> publicationYear) {
        this.id = required("id", id);
        this.title = requiredNotBlank("title", title);
        this.authorId = required("authorId", authorId);
        this.isbn = required("isbn", isbn);
        this.genre = requiredNotBlank("genre", genre);
        this.publicationYear = checkYearNotFuture("publicationYear",
              actualOrEmpty(publicationYear));
    }
}
