package com.cgdpd.library.mapper;

import static com.cgdpd.library.BookCopyTestData.aBookCopyEntity;
import static org.assertj.core.api.Assertions.assertThat;

import com.cgdpd.library.dto.book.copy.TrackingStatus;
import com.cgdpd.library.type.UserId;
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
        assertThat(result.id().value()).isEqualTo(bookCopyEntity.getId());
        assertThat(result.bookId().value()).isEqualTo(bookCopyEntity.getBookEntity().getId());
        assertThat(result.trackingStatus()).isEqualTo(
              TrackingStatus.valueOf(bookCopyEntity.getTrackingStatus()));
        assertThat(result.userId()).hasValue(UserId.of(bookCopyEntity.getUserEntity().getId()));
    }
}
