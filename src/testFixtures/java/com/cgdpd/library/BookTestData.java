package com.cgdpd.library;

import static com.cgdpd.library.AuthorTestData.AUTHOR_CHRIS_DANE;
import static com.cgdpd.library.AuthorTestData.AUTHOR_COLUMBUS_CHRISTOPHER;
import static com.cgdpd.library.AuthorTestData.AUTHOR_JANE_DANE;
import static com.cgdpd.library.AuthorTestData.AUTHOR_JOHN_DOE;
import static com.cgdpd.library.RandomIsbn.generateISBN13;

import com.cgdpd.library.dto.book.BookDTO;
import com.cgdpd.library.dto.book.CreateBookRequestDTO;
import com.cgdpd.library.entity.AuthorEntity;
import com.cgdpd.library.entity.BookEntity;
import com.cgdpd.library.type.AuthorId;
import com.cgdpd.library.type.BookId;
import com.cgdpd.library.type.Isbn13;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class BookTestData {

    private static final AtomicLong bookId = new AtomicLong(1L);
    public static final BookEntity JOHN_DOE__THE_ADVENTUROUS__1987 = aBookEntityWithRandomIsbn()
          .id(bookId.getAndIncrement())
          .authorEntity(AUTHOR_JOHN_DOE)
          .title("The Adventurous")
          .publicationYear((short) 1987)
          .build();
    public static final BookEntity JOHN_DOE__FINDER__1995 = aBookEntityWithRandomIsbn()
          .id(bookId.getAndIncrement())
          .authorEntity(AUTHOR_JOHN_DOE)
          .title("Finder")
          .publicationYear((short) 1995)
          .build();
    public static final BookEntity JANE_DANE__KILLER__2001 = aBookEntityWithRandomIsbn()
          .id(bookId.getAndIncrement())
          .authorEntity(AUTHOR_JANE_DANE)
          .title("Killer")
          .publicationYear((short) 2001)
          .build();
    public static final BookEntity JANE_DANE__SURVIVOR__2007 = aBookEntityWithRandomIsbn()
          .id(bookId.getAndIncrement())
          .authorEntity(AUTHOR_JANE_DANE)
          .title("Survivor")
          .publicationYear((short) 2007)
          .build();
    public static final BookEntity CHRIS_DANE__ONCE_AGAIN__2022 = aBookEntityWithRandomIsbn()
          .id(bookId.getAndIncrement())
          .authorEntity(AUTHOR_CHRIS_DANE)
          .title("Once Again")
          .publicationYear((short) 2022)
          .build();
    public static final BookEntity COLUMBUS_CHRISTOPHER__IN_LOVE__2011 = aBookEntityWithRandomIsbn()
          .id(bookId.getAndIncrement())
          .authorEntity(AUTHOR_COLUMBUS_CHRISTOPHER)
          .title("In Love")
          .publicationYear((short) 2011)
          .build();


    public static CreateBookRequestDTO.CreateBookRequestDTOBuilder aCreateBookRequestDTO() {
        return CreateBookRequestDTO.builder()
              .title("The Lord Of The Rings")
              .authorId(AuthorId.of(1L))
              .publicationYear(Optional.of((short) 1997))
              .isbn(Isbn13.of("978-0007632190"))
              .genre("Fiction");
    }

    public static BookEntity.BookEntityBuilder aBookEntity() {
        return aBookEntityWithRandomIsbn()
              .isbn("978-0007632190");
    }

    public static BookEntity.BookEntityBuilder aBookEntityWithRandomIsbn() {
        return BookEntity.builder()
              .id(1L)
              .title("The Lord Of The Rings")
              .authorEntity(AuthorTestData.anAuthorEntity().build())
              .publicationYear((short) 1997)
              .isbn(generateISBN13())
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

    public static BookDTO.BookDTOBuilder bookFromRequest(CreateBookRequestDTO request) {
        return BookDTO.builder()
              .id(BookId.of(1L))
              .title(request.title())
              .authorId(request.authorId())
              .publicationYear(request.publicationYear())
              .isbn(request.isbn())
              .genre(request.genre());
    }
}
