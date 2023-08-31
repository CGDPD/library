package com.cgdpd.library.dto.book;

import static com.cgdpd.library.util.OptionalUtil.actualOrEmpty;

import lombok.Builder;

import java.util.Optional;

@Builder
public record SearchBookCriteria(Optional<String> bookTitle,
                                 Optional<String> authorName,
                                 Optional<String> genre,
                                 Optional<Short> publicationYearLessThan,
                                 Optional<Short> publicationYearGreaterThan) {

    public SearchBookCriteria(Optional<String> bookTitle,
                              Optional<String> authorName,
                              Optional<String> genre,
                              Optional<Short> publicationYearLessThan,
                              Optional<Short> publicationYearGreaterThan) {
        this.bookTitle = actualOrEmpty(bookTitle);
        this.authorName = actualOrEmpty(authorName);
        this.genre = actualOrEmpty(genre);
        this.publicationYearLessThan = actualOrEmpty(publicationYearLessThan);
        this.publicationYearGreaterThan = actualOrEmpty(publicationYearGreaterThan);
    }
}
