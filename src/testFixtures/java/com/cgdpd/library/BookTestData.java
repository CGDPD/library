package com.cgdpd.library;

import static com.cgdpd.library.AuthorTestData.anAuthor;

import com.cgdp.library.dto.book.BookDTO;
import com.cgdp.library.dto.book.CreateBookRequestDTO;
import com.cgdp.library.entity.AuthorEntity;
import com.cgdp.library.entity.BookEntity;
import java.time.LocalDate;
import java.util.Optional;

public class BookTestData {

    public static CreateBookRequestDTO.CreateBookRequestDTOBuilder aCreateBookRequestDTO() {
        return CreateBookRequestDTO.builder()
              .title("The Lord Of The Rings")
              .authorId(1L)
              .publicationYear(Optional.of(LocalDate.of(1997, 1, 1)))
              .isbn("978-0007632190")
              .genre("Fiction");
    }

    public static BookEntity.BookEntityBuilder aBookEntity() {
        return BookEntity.builder()
              .id(1L)
              .title("The Lord Of The Rings")
              .authorEntity(anAuthor().build())
              .publicationYear(LocalDate.of(1997, 1, 1))
              .isbn("978-0007632190")
              .genre("Fiction");
    }

    public static BookEntity.BookEntityBuilder bookEntityFromRequest(CreateBookRequestDTO request) {
        Optional<LocalDate> publicationYear = request.publicationYear();
        LocalDate publicationYearValue = publicationYear.orElse(null);

        return BookEntity.builder()
              .id(1L)
              .title(request.title())
              .authorEntity(AuthorEntity.builder().id(request.authorId()).build())
              .publicationYear(publicationYearValue)
              .isbn(request.isbn())
              .genre(request.genre());
    }

    public static BookDTO.BookDTOBuilder bookFromRequest(CreateBookRequestDTO request) {
        Optional<LocalDate> publicationYear = request.publicationYear();
        LocalDate publicationYearValue = publicationYear.orElse(null);

        return BookDTO.builder()
              .id(1L)
              .title(request.title())
              .authorId(request.authorId())
              .publicationYear(Optional.ofNullable(publicationYearValue))
              .isbn(request.isbn())
              .genre(request.genre());
    }
}
