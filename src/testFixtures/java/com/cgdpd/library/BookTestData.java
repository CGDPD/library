package com.cgdpd.library;

import static com.cgdpd.library.AuthorTestData.anAuthor;

import com.cgdpd.library.dto.book.BookDTO;
import com.cgdpd.library.dto.book.CreateBookRequestDTO;
import com.cgdpd.library.entity.AuthorEntity;
import com.cgdpd.library.entity.BookEntity;
import java.util.Optional;

public class BookTestData {

    public static CreateBookRequestDTO.CreateBookRequestDTOBuilder aCreateBookRequestDTO() {
        return CreateBookRequestDTO.builder()
              .title("The Lord Of The Rings")
              .authorId(1L)
              .publicationYear(Optional.of((short) 1997))
              .isbn("978-0007632190")
              .genre("Fiction");
    }

    public static BookEntity.BookEntityBuilder aBookEntity() {
        return BookEntity.builder()
              .id(1L)
              .title("The Lord Of The Rings")
              .authorEntity(anAuthor().build())
              .publicationYear((short) 1997)
              .isbn("978-0007632190")
              .genre("Fiction");
    }

    public static BookEntity.BookEntityBuilder bookEntityFromRequest(CreateBookRequestDTO request) {
        return BookEntity.builder()
              .id(1L)
              .title(request.title())
              .authorEntity(AuthorEntity.builder().id(request.authorId()).build())
              .publicationYear(request.publicationYear().orElse(null))
              .isbn(request.isbn())
              .genre(request.genre());
    }

    public static BookDTO.BookDTOBuilder bookFromRequest(CreateBookRequestDTO request) {
        return BookDTO.builder()
              .id(1L)
              .title(request.title())
              .authorId(request.authorId())
              .publicationYear(request.publicationYear())
              .isbn(request.isbn())
              .genre(request.genre());
    }
}
