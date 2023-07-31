package com.cgdpd.library.mapper;

import static com.cgdpd.library.BookTestData.aBookEntity;
import static com.cgdpd.library.BookTestData.aCreateBookRequestDTO;
import static org.assertj.core.api.Assertions.assertThat;

import com.cgdpd.library.dto.book.BookDTO;
import com.cgdpd.library.dto.book.CreateBookRequestDTO;
import com.cgdpd.library.entity.BookEntity;
import org.junit.jupiter.api.Test;

class BookMapperTest {

    private final BookMapper bookMapper = new BookMapperImpl();

    @Test
    void should_map_create_book_request_to_book_entity() {
        // given
        CreateBookRequestDTO request = aCreateBookRequestDTO().build();

        // when
        BookEntity bookEntity = bookMapper.mapToBookEntity(request);

        // then
        assertThat(bookEntity.getTitle()).isEqualTo(request.title());
        assertThat(bookEntity.getAuthorEntity().getId()).isEqualTo(request.authorId().value());
        assertThat(bookEntity.getPublicationYear())
              .isEqualTo(request.publicationYear().orElseThrow());
        assertThat(bookEntity.getIsbn()).isEqualTo(request.isbn());
        assertThat(bookEntity.getGenre()).isEqualTo(request.genre());
    }

    @Test
    void should_map_book_entity_to_book_dto() {
        // given
        BookEntity bookEntity = aBookEntity().build();

        // when
        BookDTO bookDTO = bookMapper.mapToBookDTO(bookEntity);

        // then
        assertThat(bookDTO.id().value()).isEqualTo(bookEntity.getId());
        assertThat(bookDTO.title()).isEqualTo(bookEntity.getTitle());
        assertThat(bookDTO.authorId().value()).isEqualTo(bookEntity.getAuthorEntity().getId());
        assertThat(bookDTO.publicationYear()).hasValue(bookEntity.getPublicationYear());
        assertThat(bookDTO.isbn()).isEqualTo(bookEntity.getIsbn());
        assertThat(bookDTO.genre()).isEqualTo(bookEntity.getGenre());
    }
}
