package com.cgdpd.library.mapper;

import static com.cgdpd.library.BookCopyTestData.aBookCopyEntity;
import static org.assertj.core.api.Assertions.assertThat;

import com.cgdpd.library.dto.book.copy.TrackingStatus;
import org.junit.jupiter.api.Test;

class BookCopyMapperTest {

    private final BookCopyMapper bookCopyMapper = new BookCopyMapperImpl();

    @Test
    void should_map_from_entity_to_dto() {
        // given
        var bookCopyEntity = aBookCopyEntity().build();

        // when
        var result = bookCopyMapper.mapToBookCopyDto(bookCopyEntity);

        // then
        assertThat(result.id()).isEqualTo(bookCopyEntity.getId());
        assertThat(result.bookId()).isEqualTo(bookCopyEntity.getBookEntity().getId());
        assertThat(result.trackingStatus()).isEqualTo(
              TrackingStatus.valueOf(bookCopyEntity.getTrackingStatus()));
        assertThat(result.userId()).hasValue(bookCopyEntity.getUserEntity().getId());
    }
}
