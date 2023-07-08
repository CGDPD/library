package com.cgdp.library.controller;

import static com.cgdpd.library.BookTestData.aCreateBookRequestDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.cgdp.library.dto.book.BookDTO;
import com.cgdp.library.dto.book.CreateBookRequestDTO;
import com.cgdp.library.service.BookService;
import com.cgdpd.library.BookTestData;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_create_book() throws Exception {
        // given
        CreateBookRequestDTO request = aCreateBookRequestDTO().build();
        Long id = 1L;

        BookDTO book = BookTestData.bookFromRequest(request).id(id).build();
        given(bookService.createBook(request)).willReturn(book);

        // when
        var result = bookController.createBook(request);

        // then
        assertThat(result.bookId()).isEqualTo(id);
    }
}
