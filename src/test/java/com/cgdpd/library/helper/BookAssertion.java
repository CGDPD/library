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

    public static void assertThatDetailedBookDtoHasCorrectValues(DetailedBookDTO resultBook,
                                                                 BookEntity bookEntity,
                                                                 BookAvailability bookAvailability) {
        assertThat(resultBook.id()).isEqualTo(BookId.of(bookEntity.getId()));
        assertThat(resultBook.title()).isEqualTo(bookEntity.getTitle());
        assertThat(resultBook.authorId()).isEqualTo(
              AuthorId.of(bookEntity.getAuthorEntity().getId()));
        assertThat(resultBook.authorName()).isEqualTo(bookEntity.getAuthorEntity().getName());
        assertThat(resultBook.isbn()).isEqualTo(Isbn13.of(bookEntity.getIsbn()));
        assertThat(resultBook.genre()).isEqualTo(bookEntity.getGenre());
        assertThat(resultBook.availability()).isEqualTo(bookAvailability);
        assertThat(resultBook.publicationYear()).isEqualTo(
              Optional.of(bookEntity.getPublicationYear()));
    }
}
