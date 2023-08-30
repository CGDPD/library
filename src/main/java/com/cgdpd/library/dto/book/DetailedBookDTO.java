package com.cgdpd.library.dto.book;

import static com.cgdpd.library.dto.book.BookAvailability.UNAVAILABLE;
import static com.cgdpd.library.dto.book.BookAvailability.fromTrackingStatuses;
import static com.cgdpd.library.util.OptionalUtil.actualOrEmpty;
import static com.cgdpd.library.validation.Validator.checkYearNotFuture;
import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.requiredNotBlank;

import com.cgdpd.library.model.book.copy.TrackingStatus;
import com.cgdpd.library.type.AuthorId;
import com.cgdpd.library.type.BookId;
import com.cgdpd.library.type.Isbn13;

import lombok.Builder;

import java.util.Arrays;
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

    public DetailedBookDTO(long id,
                           String title,
                           long authorId,
                           String authorName,
                           String isbn,
                           String genre,
                           Object commaSeparatedTrackingStatuses,
                           Short publicationYear) {
        this(BookId.of(id),
              title,
              AuthorId.of(authorId),
              authorName,
              Isbn13.of(isbn),
              genre,
              fromCommaSeparatedString(
                    Optional.ofNullable((String) commaSeparatedTrackingStatuses)),
              Optional.ofNullable(publicationYear));
    }

    private static BookAvailability fromCommaSeparatedString(Optional<String> trackingStatuses) {
        return trackingStatuses.map(it -> fromTrackingStatuses(Arrays.stream(it.split(","))
                    .map(TrackingStatus::valueOf)
                    .toList()))
              .orElse(UNAVAILABLE);
    }
}
