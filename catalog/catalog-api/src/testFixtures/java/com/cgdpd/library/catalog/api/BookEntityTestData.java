package com.cgdpd.library.catalog.api;

import static com.cgdpd.library.catalog.api.AuthorTestData.AUTHOR_CHRIS_DANE;
import static com.cgdpd.library.catalog.api.AuthorTestData.AUTHOR_COLUMBUS_CHRISTOPHER;
import static com.cgdpd.library.catalog.api.AuthorTestData.AUTHOR_JANE_DANE;
import static com.cgdpd.library.catalog.api.AuthorTestData.AUTHOR_JOHN_DOE;

import com.cgdpd.library.catalog.api.entity.AuthorEntity;
import com.cgdpd.library.catalog.api.entity.BookEntity;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDTO;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.types.Isbn13;

import java.util.concurrent.atomic.AtomicLong;

public class BookEntityTestData {

    private static final AtomicLong bookId = new AtomicLong(1L);
    public static final BookEntity JOHN_DOE__THE_ADVENTUROUS__1987 = aBookEntityWithRandomIsbn()
          .id(bookId.getAndIncrement())
          .authorEntity(AUTHOR_JOHN_DOE)
          .title("The Adventurous")
          .publicationYear((short) 1987)
          .genre("Fiction")
          .build();
    public static final BookEntity JOHN_DOE__FINDER__1995 = aBookEntityWithRandomIsbn()
          .id(bookId.getAndIncrement())
          .authorEntity(AUTHOR_JOHN_DOE)
          .title("Finder")
          .publicationYear((short) 1995)
          .genre("Mystery")
          .build();
    public static final BookEntity JANE_DANE__KILLER__2001 = aBookEntityWithRandomIsbn()
          .id(bookId.getAndIncrement())
          .authorEntity(AUTHOR_JANE_DANE)
          .title("Killer")
          .publicationYear((short) 2001)
          .genre("Drama")
          .build();
    public static final BookEntity JANE_DANE__SURVIVOR__2007 = aBookEntityWithRandomIsbn()
          .id(bookId.getAndIncrement())
          .authorEntity(AUTHOR_JANE_DANE)
          .title("Survivor")
          .publicationYear((short) 2007)
          .genre("Suspense")
          .build();
    public static final BookEntity CHRIS_DANE__ONCE_AGAIN__2022 = aBookEntityWithRandomIsbn()
          .id(bookId.getAndIncrement())
          .authorEntity(AUTHOR_CHRIS_DANE)
          .title("Once Again")
          .publicationYear((short) 2022)
          .genre("Suspense")
          .build();
    public static final BookEntity COLUMBUS_CHRISTOPHER__IN_LOVE__2011 = aBookEntityWithRandomIsbn()
          .id(bookId.getAndIncrement())
          .authorEntity(AUTHOR_COLUMBUS_CHRISTOPHER)
          .title("In Love")
          .publicationYear((short) 2011)
          .genre("Romance")
          .build();

    public static BookEntity.BookEntityBuilder aBookEntity() {
        return aBookEntityWithRandomIsbn()
              .isbn("978-0007632190");
    }

    public static BookEntity.BookEntityBuilder aBookEntityWithRandomIsbn() {
        return BookEntity.builder()
              .title("The Lord Of The Rings")
              .authorEntity(AuthorTestData.anAuthorEntity().build())
              .publicationYear((short) 1997)
              .isbn(Isbn13.random().value())
              .genre("Fiction");
    }

    public static BookEntity.BookEntityBuilder bookEntityFromRequest(CreateBookRequestDTO request) {
        return BookEntity.builder()
              .id(1L)
              .title(request.title())
              .authorEntity(AuthorEntity.builder().id(request.authorId().value()).build())
              .publicationYear(request.publicationYear().orElse((short) 0))
              .isbn(request.isbn().value())
              .genre(request.genre());
    }

    public static BookEntity.BookEntityBuilder bookEntityFromBook(Book book) {
        return BookEntity.builder()
              .id(book.id().value())
              .title(book.title())
              .authorEntity(AuthorEntity.builder().id(book.authorId().value()).build())
              .publicationYear(book.publicationYear().orElse((short) 0))
              .isbn(book.isbn().value())
              .genre(book.genre());
    }
}
