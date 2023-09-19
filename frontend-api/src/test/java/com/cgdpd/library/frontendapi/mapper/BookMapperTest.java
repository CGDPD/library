package com.cgdpd.library.frontendapi.mapper;

import static com.cgdpd.library.catalog.domain.BookTestData.aDetailedBookDto;
import static com.cgdpd.library.frontendapi.dto.BookAvailability.fromTrackingStatusList;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BookMapperTest {

    private final BookMapper bookMapper = new BookMapperImpl();

    @Test
    void should_map_detailed_book_dto_to_book_view_dto() {
        // given
        var detailedBookDto = aDetailedBookDto().build();

        // when
        var result = bookMapper.mapToBookView(detailedBookDto);

        // then
        assertThat(result.id()).isEqualTo(detailedBookDto.id());
        assertThat(result.title()).isEqualTo(detailedBookDto.title());
        assertThat(result.authorId()).isEqualTo(detailedBookDto.authorId());
        assertThat(result.authorName()).isEqualTo(detailedBookDto.authorName());
        assertThat(result.isbn()).isEqualTo(detailedBookDto.isbn());
        assertThat(result.genre()).isEqualTo(detailedBookDto.genre());
        assertThat(result.bookAvailability())
              .isEqualTo(fromTrackingStatusList(detailedBookDto.trackingStatusList()));
        assertThat(result.publicationYear()).isEqualTo(detailedBookDto.publicationYear());
    }
}
