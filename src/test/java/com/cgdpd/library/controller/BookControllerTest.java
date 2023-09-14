package com.cgdpd.library.controller;

import static com.cgdpd.library.BookTestData.aCreateBookRequestDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
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
        var searchCriteria = Optional.of(SearchBookCriteria.builder().build());
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

        given(bookService.findDetailedBooksPage(paginationCriteria,
              searchCriteria.orElse(null))).willReturn(
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
        var actualResponse = bookController.searchBook(Optional.empty(), paginationCriteria);

        // then
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void should_return_status_ok_when_search_book() throws Exception {
        // given
        var searchCriteria = Optional.of(SearchBookCriteria.builder().build());
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

        given(bookService.findDetailedBooksPage(paginationCriteria,
              searchCriteria.orElse(null))).willReturn(expectedResponse);

        // when
        var requestBuilder = MockMvcRequestBuilders
              .get("/books")
              .param("searchBookCriteria", searchCriteria.toString())
              .param("paginationCriteria", paginationCriteria.toString())
              .accept(MediaType.APPLICATION_JSON);

        var resultActions = mockMvc.perform(requestBuilder);

        // then
        resultActions.andExpect(status().isOk());
    }
}
