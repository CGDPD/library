package com.cgdpd.library.catalog.domain.book.dto;

import static com.cgdpd.library.common.util.OptionalUtil.actualOrEmpty;

import lombok.Builder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

@Builder
public record SearchBookCriteria(Optional<String> bookTitle,
                                 Optional<String> authorName,
                                 Optional<String> genre,
                                 Optional<Short> publicationYearLessThan,
                                 Optional<Short> publicationYearGreaterThan) implements
      QueryParamsConvertible {

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

    @Override
    public MultiValueMap<String, String> toQueryParams() {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        bookTitle.ifPresent(value -> queryParams.add("bookTitle", value));
        authorName.ifPresent(value -> queryParams.add("authorName", value));
        genre.ifPresent(value -> queryParams.add("genre", value));
        publicationYearLessThan.ifPresent(
              value -> queryParams.add("publicationYearLessThan", value.toString()));
        publicationYearGreaterThan.ifPresent(
              value -> queryParams.add("publicationYearGreaterThan", value.toString()));
        return queryParams;
    }
}
