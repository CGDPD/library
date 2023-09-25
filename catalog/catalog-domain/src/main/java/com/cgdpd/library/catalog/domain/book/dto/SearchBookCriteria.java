package com.cgdpd.library.catalog.domain.book.dto;

import static com.cgdpd.library.common.util.OptionalUtil.actualOrEmpty;

import com.cgdpd.library.catalog.domain.book.model.Book;

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

    public static SearchBookCriteria empty() {
        return new SearchBookCriteria(Optional.empty(), Optional.empty(),
              Optional.empty(), Optional.empty(), Optional.empty());
    }

    public boolean matches(Book book) {
        return bookTitle.map(title -> title.equals(book.title())).orElse(true)
              && authorName.map(name -> name.equals(book.authorId().value().toString()))
              .orElse(true)
              && genre.map(genre -> genre.equals(book.genre())).orElse(true)
              && publicationYearLessThan.map(
              year -> year > book.publicationYear().orElse((short) 0)).orElse(true)
              && publicationYearGreaterThan.map(
              year -> year < book.publicationYear().orElse(Short.MAX_VALUE)).orElse(true);
    }
}
