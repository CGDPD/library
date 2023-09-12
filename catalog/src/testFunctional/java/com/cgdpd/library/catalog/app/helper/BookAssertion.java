package com.cgdpd.library.catalog.app.helper;

import static org.assertj.core.api.Assertions.assertThat;

import com.cgdpd.library.catalog.app.entity.BookEntity;
import com.cgdpd.library.catalog.domain.author.AuthorId;
import com.cgdpd.library.catalog.domain.book.dto.BookAvailability;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.model.BookId;
import com.cgdpd.library.types.Isbn13;

import java.util.Optional;

public class BookAssertion {

    public static void assertThatDetailedBookDtoHasCorrectValues(
          DetailedBookDto resultDetailedBookDto,
          BookEntity bookEntity,
          BookAvailability bookAvailability) {

        assertThat(resultDetailedBookDto.id()).isEqualTo(BookId.of(bookEntity.getId()));
        assertThat(resultDetailedBookDto.title()).isEqualTo(bookEntity.getTitle());
        assertThat(resultDetailedBookDto.authorId()).isEqualTo(
              AuthorId.of(bookEntity.getAuthorEntity().getId()));
        assertThat(resultDetailedBookDto.authorName()).isEqualTo(
              bookEntity.getAuthorEntity().getName());
        assertThat(resultDetailedBookDto.isbn()).isEqualTo(Isbn13.of(bookEntity.getIsbn()));
        assertThat(resultDetailedBookDto.genre()).isEqualTo(bookEntity.getGenre());
        assertThat(resultDetailedBookDto.availability()).isEqualTo(bookAvailability);
        assertThat(resultDetailedBookDto.publicationYear()).isEqualTo(
              Optional.of(bookEntity.getPublicationYear()));
    }
}