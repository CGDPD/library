package com.cgdp.library.controller;

import static com.cgdpd.library.BookTestData.aCreateBookRequestDTO;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cgdp.library.dto.book.BookDTO;
import com.cgdp.library.dto.book.CreateBookRequestDTO;
import com.cgdp.library.service.BookService;
import com.cgdpd.library.BookTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }


    @Test
    public void should_create_book() throws Exception {
        // given
        CreateBookRequestDTO request = aCreateBookRequestDTO().build();
        Long id = 1L;

        BookDTO book = BookTestData.bookFromRequest(request).id(id).build();
        given(bookService.createBook(request)).willReturn(book);

        // when
        ResultActions resultActions = mockMvc.perform(post("/books")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.bookId", is(id.intValue())));
    }

    @Test
    public void should_return_bad_request_when_required_field_is_missing() throws Exception {
        // given
        String badRequestMissingTitle = """
              {
                  "authorId": 1,
                  "publicationYear": "2023-01-01",
                  "isbn": "978-0-7475-9105-4’",
                  "genre": "Action"
              }
              """;

        // when
        ResultActions resultActions = mockMvc.perform(post("/books")
              .contentType(MediaType.APPLICATION_JSON)
              .content(badRequestMissingTitle));

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_bad_request_when_isbn_is_not_valid() throws Exception {
        // given
        String badRequestInvalidIsbn = """
              {
                  "title": "Lord Of The Rings"
                  "authorId": 1,
                  "publicationYear": "2023-01-01",
                  "isbn": "978-0-7475-9105’",
                  "genre": "Action"
              }
              """;

        // when
        ResultActions resultActions = mockMvc.perform(post("/books")
              .contentType(MediaType.APPLICATION_JSON)
              .content(badRequestInvalidIsbn));

        // then
        resultActions.andExpect(status().isBadRequest());
    }
}
