package com.cgdpd.library.catalog.app.controller;

import static com.cgdpd.library.catalog.domain.BookTestData.aCreateBookRequestDto;
import static com.cgdpd.library.catalog.domain.BookTestData.bookFromRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.cgdpd.library.catalog.app.service.BookService;
import com.cgdpd.library.catalog.domain.book.model.BookId;

import org.junit.jupiter.api.Test;

class BookControllerTest {

    private final BookService bookService = mock(BookService.class);

    private final BookController bookController = new BookController(bookService);

    @Test
    void should_create_book() {
        // given
        var request = aCreateBookRequestDto().build();
        var id = BookId.of(1L);
        var book = bookFromRequest(request).id(id).build();
        given(bookService.createBook(request)).willReturn(book);

        // when
        var resultCreatedBook = bookController.createBook(request);

        // then
        assertThat(resultCreatedBook).isEqualTo(book);
    }
}
