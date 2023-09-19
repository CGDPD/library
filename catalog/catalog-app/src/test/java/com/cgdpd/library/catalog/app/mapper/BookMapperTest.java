package com.cgdpd.library.catalog.app.mapper;

import static com.cgdpd.library.catalog.app.BookCopyEntityTestData.aBookCopyEntity;
import static com.cgdpd.library.catalog.app.BookEntityTestData.aBookEntity;
import static com.cgdpd.library.catalog.app.helper.BookAssertion.assertThatDetailedBookDtoHasCorrectValues;
import static com.cgdpd.library.catalog.domain.BookTestData.aCreateBookRequestDto;
import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.AVAILABLE;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;

class BookMapperTest {

    private final BookMapper bookMapper = new BookMapperImpl();

    @Test
    void should_map_create_book_request_dto_to_book_entity() {
        // given
        var request = aCreateBookRequestDto().build();

        // when
        var resultBookEntity = bookMapper.mapToBookEntity(request);

        // then
        assertThat(resultBookEntity.getTitle()).isEqualTo(request.title());
        assertThat(resultBookEntity.getAuthorEntity().getId())
              .isEqualTo(request.authorId().value());
        assertThat(resultBookEntity.getPublicationYear())
              .isEqualTo(request.publicationYear().orElseThrow());
        assertThat(resultBookEntity.getIsbn()).isEqualTo(request.isbn().value());
        assertThat(resultBookEntity.getGenre()).isEqualTo(request.genre());
    }

    @Test
    void should_map_book_entity_to_book() {
        // given
        var bookEntity = aBookEntity().id(1L).build();

        // when
        var resultBook = bookMapper.mapToBook(bookEntity);

        // then
        assertThat(resultBook.id().value()).isEqualTo(bookEntity.getId());
        assertThat(resultBook.title()).isEqualTo(bookEntity.getTitle());
        assertThat(resultBook.authorId().value()).isEqualTo(bookEntity.getAuthorEntity().getId());
        assertThat(resultBook.publicationYear()).hasValue(bookEntity.getPublicationYear());
        assertThat(resultBook.isbn().value()).isEqualTo(bookEntity.getIsbn());
        assertThat(resultBook.genre()).isEqualTo(bookEntity.getGenre());
    }

    @Test
    void should_map_book_entity_to_detailed_book_dto() {
        // given
        var bookId = 1L;
        var bookEntity = aBookEntity()
              .id(bookId)
              .bookCopyEntities(List.of(
                    aBookCopyEntity(bookId)
                          .trackingStatus(AVAILABLE.name())
                          .build()))
              .build();

        // when
        var resultDetailedBookDto = bookMapper.mapToDetailedBookDto(bookEntity);

        // then
        assertThatDetailedBookDtoHasCorrectValues(
              resultDetailedBookDto,
              bookEntity,
              List.of(AVAILABLE));
    }

    @Test
    void should_map_book_entity_without_copies_to_detailed_book_dto() {
        // given
        var bookEntity = aBookEntity()
              .id(1L)
              .bookCopyEntities(emptyList())
              .build();

        // when
        var resultDetailedBookDto = bookMapper.mapToDetailedBookDto(bookEntity);

        // then
        assertThatDetailedBookDtoHasCorrectValues(resultDetailedBookDto, bookEntity, emptyList());
    }
}
