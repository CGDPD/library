package com.cgdp.library.mapper;

import static com.cgdpd.library.BookTestData.aBookEntity;
import static com.cgdpd.library.BookTestData.aCreateBookRequestDTO;
import static org.assertj.core.api.Assertions.assertThat;

import com.cgdp.library.dto.book.BookDTO;
import com.cgdp.library.dto.book.CreateBookRequestDTO;
import com.cgdp.library.entity.BookEntity;
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
        assertThat(bookEntity.getAuthorEntity().getId()).isEqualTo(request.authorId());
        assertThat(bookEntity.getPublicationYear()).isEqualTo(request.publicationYear());
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
        assertThat(bookDTO.id()).isEqualTo(bookEntity.getId());
        assertThat(bookDTO.title()).isEqualTo(bookEntity.getTitle());
        assertThat(bookDTO.authorId()).isEqualTo(bookEntity.getAuthorEntity().getId());
        assertThat(bookDTO.publicationYear()).isEqualTo(bookEntity.getPublicationYear());
        assertThat(bookDTO.isbn()).isEqualTo(bookEntity.getIsbn());
        assertThat(bookDTO.genre()).isEqualTo(bookEntity.getGenre());
    }
}
