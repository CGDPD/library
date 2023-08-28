package com.cgdpd.library.controller;

import static com.cgdpd.library.BookTestData.aCreateBookRequestDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.cgdpd.library.BookTestData;
import com.cgdpd.library.service.BookService;
import com.cgdpd.library.type.BookId;
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
        var request = aCreateBookRequestDTO().build();
        var id = BookId.of(1L);
        var book = BookTestData.bookFromRequest(request).id(id).build();
        given(bookService.createBook(request)).willReturn(book);

        // when
        var result = bookController.createBook(request);

        // then
        assertThat(result.bookId()).isEqualTo(id);
    }
}
