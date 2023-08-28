package com.cgdpd.library.model.book;

import static com.cgdpd.library.util.OptionalUtil.actualOrEmpty;
import static com.cgdpd.library.validation.Validator.checkYearNotFuture;
import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.requiredNotBlank;

import com.cgdpd.library.type.AuthorId;
import com.cgdpd.library.type.BookId;
import com.cgdpd.library.type.Isbn13;
import java.util.Optional;
import lombok.Builder;

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
