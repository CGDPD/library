package com.cgdpd.library.catalog.domain.book.dto;

import static com.cgdpd.library.common.util.OptionalUtil.actualOrEmpty;
import static com.cgdpd.library.common.validation.Validator.checkYearNotFuture;
import static com.cgdpd.library.common.validation.Validator.required;
import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;

import com.cgdpd.library.catalog.domain.author.AuthorId;
import com.cgdpd.library.catalog.domain.book.model.BookId;
import com.cgdpd.library.types.Isbn13;

import lombok.Builder;

import java.util.Optional;

@Builder
public record DetailedBookDTO(BookId id,
                              String title,
                              AuthorId authorId,
                              String authorName,
                              Isbn13 isbn,
                              String genre,
                              BookAvailability availability,
                              Optional<Short> publicationYear) {
    // TODO: 29/08/2023 LIB-24 Due dates

    public DetailedBookDTO(BookId id,
                           String title,
                           AuthorId authorId,
                           String authorName,
                           Isbn13 isbn,
                           String genre,
                           BookAvailability availability,
                           Optional<Short> publicationYear) {
        this.id = required("id", id);
        this.title = requiredNotBlank("title", title);
        this.authorId = required("authorId", authorId);
        this.authorName = requiredNotBlank("authorName", authorName);
        this.isbn = required("isbn", isbn);
        this.genre = requiredNotBlank("genre", genre);
        this.availability = required("availability", availability);
        this.publicationYear = checkYearNotFuture("publicationYear",
              actualOrEmpty(publicationYear));
    }
}
