package com.cgdpd.library.catalog.app.helper;

import static org.assertj.core.api.Assertions.assertThat;

import com.cgdpd.library.catalog.app.entity.BookEntity;
import com.cgdpd.library.catalog.domain.author.AuthorId;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.model.BookId;
import com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus;
import com.cgdpd.library.types.Isbn13;

import java.util.List;
import java.util.Optional;

public class BookAssertion {

    public static void assertThatDetailedBookDtoHasCorrectValues(
          DetailedBookDto resultDetailedBookDto,
          BookEntity bookEntity,
          List<TrackingStatus> trackingStatusList) {

        assertThat(resultDetailedBookDto.id()).isEqualTo(BookId.of(bookEntity.getId()));
        assertThat(resultDetailedBookDto.title()).isEqualTo(bookEntity.getTitle());
        assertThat(resultDetailedBookDto.authorId()).isEqualTo(
              AuthorId.of(bookEntity.getAuthorEntity().getId()));
        assertThat(resultDetailedBookDto.authorName()).isEqualTo(
              bookEntity.getAuthorEntity().getName());
        assertThat(resultDetailedBookDto.isbn()).isEqualTo(Isbn13.of(bookEntity.getIsbn()));
        assertThat(resultDetailedBookDto.genre()).isEqualTo(bookEntity.getGenre());
        assertThat(resultDetailedBookDto.trackingStatusList())
              .containsExactlyInAnyOrderElementsOf(trackingStatusList);
        assertThat(resultDetailedBookDto.publicationYear()).isEqualTo(
              Optional.of(bookEntity.getPublicationYear()));
    }
}
