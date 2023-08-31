package com.cgdpd.library.mapper;

import static com.cgdpd.library.BookCopyTestData.aBookCopyEntity;
import static com.cgdpd.library.UserTestData.aUserEntity;
import static com.cgdpd.library.model.book.copy.TrackingStatus.CHECKED_OUT;
import static org.assertj.core.api.Assertions.assertThat;

import com.cgdpd.library.model.book.copy.TrackingStatus;
import com.cgdpd.library.type.UserId;

import org.junit.jupiter.api.Test;

class BookCopyMapperTest {

    private final BookCopyMapper bookCopyMapper = new BookCopyMapperImpl();

    @Test
    void should_map_book_copy_entity_to_book_copy() {
        // given
        var bookCopyEntity = aBookCopyEntity(1L)
              .id(1L)
              .userEntity(aUserEntity(1L).build())
              .trackingStatus(CHECKED_OUT.name())
              .build();

        // when
        var result = bookCopyMapper.mapToBookCopy(bookCopyEntity);

        // then
        assertThat(result.id().value()).isEqualTo(bookCopyEntity.getId());
        assertThat(result.bookId().value()).isEqualTo(bookCopyEntity.getBookEntity().getId());
        assertThat(result.trackingStatus()).isEqualTo(
              TrackingStatus.valueOf(bookCopyEntity.getTrackingStatus()));
        assertThat(result.userId()).hasValue(UserId.of(bookCopyEntity.getUserEntity().getId()));
    }
}
