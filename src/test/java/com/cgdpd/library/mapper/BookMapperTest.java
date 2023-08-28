package com.cgdpd.library.mapper;

import static com.cgdpd.library.BookTestData.aBookEntity;
import static com.cgdpd.library.BookTestData.aCreateBookRequestDTO;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BookMapperTest {

    private final BookMapper bookMapper = new BookMapperImpl();

    @Test
    void should_map_create_book_request_dto_to_book_entity() {
        // given
        var request = aCreateBookRequestDTO().build();

        // when
        var bookEntity = bookMapper.mapToBookEntity(request);

        // then
        assertThat(bookEntity.getTitle()).isEqualTo(request.title());
        assertThat(bookEntity.getAuthorEntity().getId()).isEqualTo(request.authorId().value());
        assertThat(bookEntity.getPublicationYear()).isEqualTo(request.publicationYear().orElseThrow());
        assertThat(bookEntity.getIsbn()).isEqualTo(request.isbn().value());
        assertThat(bookEntity.getGenre()).isEqualTo(request.genre());
    }

    @Test
    void should_map_book_entity_to_book() {
        // given
        var bookEntity = aBookEntity().build();

        // when
        var book = bookMapper.mapToBook(bookEntity);

        // then
        assertThat(book.id().value()).isEqualTo(bookEntity.getId());
        assertThat(book.title()).isEqualTo(bookEntity.getTitle());
        assertThat(book.authorId().value()).isEqualTo(bookEntity.getAuthorEntity().getId());
        assertThat(book.publicationYear()).hasValue(bookEntity.getPublicationYear());
        assertThat(book.isbn().value()).isEqualTo(bookEntity.getIsbn());
        assertThat(book.genre()).isEqualTo(bookEntity.getGenre());
    }
}
