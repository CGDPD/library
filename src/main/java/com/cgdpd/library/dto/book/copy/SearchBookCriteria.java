package com.cgdpd.library.dto.book.copy;

import static com.cgdpd.library.util.OptionalUtil.actualOrEmpty;

import com.cgdpd.library.type.Isbn13;
import java.util.Optional;
import lombok.Builder;

@Builder
public record SearchBookCriteria(Optional<String> bookTitle,
                                 Optional<String> authorName,
                                 Optional<String> genre,
                                 Optional<Isbn13> isbn13,
                                 Optional<Short> publicationYearLessThan,
                                 Optional<Short> publicationYearGreaterThan) {
    public SearchBookCriteria(Optional<String> bookTitle,
                              Optional<String> authorName,
                              Optional<String> genre,
                              Optional<Isbn13> isbn13,
                              Optional<Short> publicationYearLessThan,
                              Optional<Short> publicationYearGreaterThan) {
        this.bookTitle = actualOrEmpty(bookTitle);
        this.authorName = actualOrEmpty(authorName);
        this.genre = actualOrEmpty(genre);
        this.isbn13 = actualOrEmpty(isbn13);
        this.publicationYearLessThan = actualOrEmpty(publicationYearLessThan);
        this.publicationYearGreaterThan = actualOrEmpty(publicationYearGreaterThan);
    }
}
