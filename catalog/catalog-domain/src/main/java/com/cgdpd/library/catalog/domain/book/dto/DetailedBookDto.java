package com.cgdpd.library.catalog.domain.book.dto;

import static com.cgdpd.library.common.util.OptionalUtil.actualOrEmpty;
import static com.cgdpd.library.common.validation.Validator.checkYearNotFuture;
import static com.cgdpd.library.common.validation.Validator.required;
import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;

import com.cgdpd.library.catalog.domain.author.AuthorId;
import com.cgdpd.library.catalog.domain.book.model.BookId;
import com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus;
import com.cgdpd.library.common.type.Isbn13;

import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder
public record DetailedBookDto(BookId id,
                              String title,
                              AuthorId authorId,
                              String authorName,
                              Isbn13 isbn,
                              String genre,
                              List<TrackingStatus> trackingStatusList,
                              Optional<Short> publicationYear) {
    // TODO: 29/08/2023 LIB-24 Due dates

    public DetailedBookDto(BookId id,
                           String title,
                           AuthorId authorId,
                           String authorName,
                           Isbn13 isbn,
                           String genre,
                           List<TrackingStatus> trackingStatusList,
                           Optional<Short> publicationYear) {
        this.id = required("id", id);
        this.title = requiredNotBlank("title", title);
        this.authorId = required("authorId", authorId);
        this.authorName = requiredNotBlank("authorName", authorName);
        this.isbn = required("isbn", isbn);
        this.genre = requiredNotBlank("genre", genre);
        this.trackingStatusList = required("trackingStatusList", trackingStatusList);
        this.publicationYear = checkYearNotFuture("publicationYear",
              actualOrEmpty(publicationYear));
    }
}
