package com.cgdpd.library.catalog.domain;

import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.AVAILABLE;

import com.cgdpd.library.catalog.domain.author.AuthorId;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.catalog.domain.book.model.BookId;
import com.cgdpd.library.common.type.Isbn13;

import java.util.List;
import java.util.Optional;

public class BookTestData {

    public static Book.BookBuilder aBook() {
        return aBookWithRandomIsbn()
              .isbn(Isbn13.of("978-0007632190"));
    }

    public static Book.BookBuilder aBookWithRandomIsbn() {
        return Book.builder()
              .id(BookId.of(1L))
              .title("The Lord Of The Rings")
              .authorId(AuthorId.of(1L))
              .publicationYear(Optional.of((short) 1997))
              .isbn(Isbn13.random())
              .genre("Fiction");
    }

    public static CreateBookRequestDto.CreateBookRequestDtoBuilder aCreateBookRequestDto() {
        return CreateBookRequestDto.builder()
              .title("The Lord Of The Rings")
              .authorId(AuthorId.of(1L))
              .publicationYear(Optional.of((short) 1997))
              .isbn(Isbn13.of("978-0007632190"))
              .genre("Fiction");
    }

    public static DetailedBookDto.DetailedBookDtoBuilder aDetailedBookDto() {
        return DetailedBookDto.builder()
              .id(BookId.of(1L))
              .title("The Lord Of The Rings")
              .authorId(AuthorId.of(1L))
              .authorName("J. R. R. Tolkien")
              .isbn(Isbn13.random())
              .genre("Fiction")
              .trackingStatusList(List.of(AVAILABLE))
              .publicationYear(Optional.of((short) 1954));
    }


    public static Book.BookBuilder bookFromRequest(CreateBookRequestDto request) {
        return Book.builder()
              .id(BookId.of(1L))
              .title(request.title())
              .authorId(request.authorId())
              .publicationYear(request.publicationYear())
              .isbn(request.isbn())
              .genre(request.genre());
    }
}
