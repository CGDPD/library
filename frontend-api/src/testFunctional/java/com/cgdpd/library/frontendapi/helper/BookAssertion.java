package com.cgdpd.library.frontendapi.helper;

import static org.assertj.core.api.Assertions.assertThat;

import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.frontendapi.dto.BookAvailability;
import com.cgdpd.library.frontendapi.dto.BookViewDto;

public class BookAssertion {

    public static void assertThatDetailedBookDtoHasCorrectValues(
          BookViewDto resultBookViewDto,
          Book book,
          Author author,
          BookAvailability bookAvailability) {

        assertThat(resultBookViewDto.id()).isEqualTo(book.id());
        assertThat(resultBookViewDto.title()).isEqualTo(book.title());
        assertThat(resultBookViewDto.authorId()).isEqualTo(author.id());
        assertThat(resultBookViewDto.authorName()).isEqualTo(author.name());
        assertThat(resultBookViewDto.isbn()).isEqualTo(book.isbn());
        assertThat(resultBookViewDto.genre()).isEqualTo(book.genre());
        assertThat(resultBookViewDto.bookAvailability()).isEqualTo(bookAvailability);
        assertThat(resultBookViewDto.publicationYear()).isEqualTo(book.publicationYear());
    }
}
