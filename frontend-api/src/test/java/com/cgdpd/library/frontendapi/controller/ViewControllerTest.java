package com.cgdpd.library.frontendapi.controller;

import static com.cgdpd.library.catalog.domain.BookTestData.aDetailedBookDto;
import static com.cgdpd.library.frontendapi.dto.BookAvailability.fromTrackingStatusList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.cgdpd.library.frontendapi.mapper.BookMapper;
import com.cgdpd.library.frontendapi.mapper.BookMapperImpl;
import com.cgdpd.library.frontendapi.service.BookService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ViewControllerTest {

    private final BookService bookService = Mockito.mock(BookService.class);
    private final BookMapper bookMapper = new BookMapperImpl();
    private final ViewController viewController = new ViewController(bookService, bookMapper);

    @Test
    void should_return_a_book_view_dto() {
        // given
        var detailedBookDto = aDetailedBookDto().build();
        var isbn13 = detailedBookDto.isbn();
        given(bookService.getDetailedBookDtoByIsbn13(isbn13)).willReturn(detailedBookDto);

        // when
        var result = viewController.detailedBookDto(isbn13);

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
