package com.cgdpd.library.controller;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cgdpd.library.AuthorTestData;
import com.cgdpd.library.BookTestData;
import com.cgdpd.library.FunctionalTest;
import com.cgdpd.library.dto.book.CreateBookRequestDTO;
import com.cgdpd.library.entity.AuthorEntity;
import com.cgdpd.library.entity.BookEntity;
import com.cgdpd.library.repository.AuthorRepository;
import com.cgdpd.library.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc
public class BookControllerFunctionalTest extends FunctionalTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void should_create_book_and_return_id() throws Exception {
        // given
        CreateBookRequestDTO request = BookTestData.aCreateBookRequestDTO().build();

        AuthorEntity authorEntity = AuthorTestData.anAuthor().build();
        authorRepository.save(authorEntity);

        // when
        ResultActions resultActions = mockMvc.perform(post("/books")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.bookId", is(Long.class)).exists());

        Long id = getIdFromResult(resultActions);
        BookEntity bookEntity = bookRepository.findById(id).orElseThrow();
        assertThat(bookEntity.getTitle()).isEqualTo(request.title());
        assertThat(bookEntity.getAuthorEntity()).isEqualTo(authorEntity);
        assertThat(bookEntity.getPublicationYear())
              .isEqualTo(request.publicationYear().orElseThrow());
        assertThat(bookEntity.getIsbn()).isEqualTo(request.isbn());
        assertThat(bookEntity.getGenre()).isEqualTo(request.genre());
    }

    @Test
    public void should_return_not_found_code_when_author_does_not_exist() throws Exception {
        // given
        CreateBookRequestDTO request = BookTestData.aCreateBookRequestDTO().build();

        // when
        ResultActions resultActions = mockMvc.perform(post("/books")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions
              .andExpect(status().isNotFound());
    }

    private Long getIdFromResult(ResultActions resultActions)
          throws UnsupportedEncodingException, JSONException {
        MvcResult mvcResult = resultActions.andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(jsonResponse);
        return jsonObject.getLong("bookId");
    }
}
