package com.cgdpd.library.catalog.app.controller;

import static com.cgdpd.library.catalog.domain.BookTestData.aCreateBookRequestDto;
import static com.cgdpd.library.catalog.domain.BookTestData.bookFromRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.cgdpd.library.catalog.app.service.BookService;
import com.cgdpd.library.catalog.domain.book.model.BookId;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void should_create_book() {
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
