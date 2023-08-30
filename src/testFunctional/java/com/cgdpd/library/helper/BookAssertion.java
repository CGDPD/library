package com.cgdpd.library.helper;

import static org.assertj.core.api.Assertions.assertThat;

import com.cgdpd.library.dto.book.BookAvailability;
import com.cgdpd.library.dto.book.DetailedBookDTO;
import com.cgdpd.library.entity.BookEntity;
import com.cgdpd.library.type.AuthorId;
import com.cgdpd.library.type.BookId;
import com.cgdpd.library.type.Isbn13;

import java.util.Optional;

public class BookAssertion {

    public static void assertThatDetailedBookDtoHasCorrectValues(DetailedBookDTO resultDetailedBookDto,
                                                                 BookEntity bookEntity,
                                                                 BookAvailability bookAvailability) {
        assertThat(resultDetailedBookDto.id()).isEqualTo(BookId.of(bookEntity.getId()));
        assertThat(resultDetailedBookDto.title()).isEqualTo(bookEntity.getTitle());
        assertThat(resultDetailedBookDto.authorId())
              .isEqualTo(AuthorId.of(bookEntity.getAuthorEntity().getId()));
        assertThat(resultDetailedBookDto.authorName())
              .isEqualTo(bookEntity.getAuthorEntity().getName());
        assertThat(resultDetailedBookDto.isbn()).isEqualTo(Isbn13.of(bookEntity.getIsbn()));
        assertThat(resultDetailedBookDto.genre()).isEqualTo(bookEntity.getGenre());
        assertThat(resultDetailedBookDto.availability()).isEqualTo(bookAvailability);
        assertThat(resultDetailedBookDto.publicationYear())
              .isEqualTo(Optional.of(bookEntity.getPublicationYear()));
    }
}
