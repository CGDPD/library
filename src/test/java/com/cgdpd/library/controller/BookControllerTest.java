package com.cgdpd.library.controller;

import static com.cgdpd.library.BookTestData.aCreateBookRequestDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.cgdpd.library.BookTestData;
import com.cgdpd.library.dto.book.DetailedBookDTO;
import com.cgdpd.library.dto.book.SearchBookCriteria;
import com.cgdpd.library.dto.pagination.PagedResponse;
import com.cgdpd.library.dto.pagination.PaginationCriteria;
import com.cgdpd.library.service.BookService;
import com.cgdpd.library.type.BookId;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

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
    void should_create_book() {
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

    @Test
    void should_return_book_with_criteria() {
        // given
        var searchCriteria = SearchBookCriteria.builder().build();
        var paginationCriteria = PaginationCriteria.builder()
              .pageIndex(0)
              .pageSize(10)
              .build();
        var expectedResponse = PagedResponse.<DetailedBookDTO>builder()
              .content(new ArrayList<>())
              .pageNumber(0)
              .pageSize(10)
              .totalElements(0)
              .totalPages(0)
              .build();

        given(bookService.findDetailedBooksPage(paginationCriteria, searchCriteria)).willReturn(
              expectedResponse);

        // when
        var actualResponse = bookController.searchBook(searchCriteria, paginationCriteria);

        // then
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void should_return_book_without_criteria() {
        // given
        var paginationCriteria = PaginationCriteria.builder()
              .pageIndex(0)
              .pageSize(10)
              .build();
        var expectedResponse = PagedResponse.<DetailedBookDTO>builder()
              .content(new ArrayList<>())
              .pageNumber(0)
              .pageSize(10)
              .totalElements(0)
              .totalPages(0)
              .build();

        given(bookService.findDetailedBooksPage(paginationCriteria, null)).willReturn(
              expectedResponse);

        // when
        var actualResponse = bookController.searchBook(null, paginationCriteria);

        // then
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}
