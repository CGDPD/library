package com.cgdpd.library.catalog.app.controller;

import static com.cgdpd.library.catalog.app.helper.TestUtils.getObjectFromResultActions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cgdpd.library.catalog.app.FunctionalTest;
import com.cgdpd.library.catalog.app.repository.AuthorRepository;
import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public class AuthorControllerFunctionalTest extends FunctionalTest {

    private static final String BASE_ENDPOINT = "/author";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_create_author_and_return_name() throws Exception {
        // given
        var authorName = "John Doe";
        var createAuthorRequestDto = new CreateAuthorRequestDto(authorName);

        // when
        var resultActions = mockMvc.perform(post(BASE_ENDPOINT)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(createAuthorRequestDto)));
        // then
        resultActions
              .andExpect(status().isCreated());

        var resultCreatedAuthor = getObjectFromResultActions(resultActions, Author.class,
              objectMapper);
        assertThat(resultCreatedAuthor.name()).isEqualTo(authorName);

        var authorEntity = authorRepository.findById(resultCreatedAuthor.id().value());
        assertThat(authorEntity).isPresent();
        assertThat(authorEntity.get().getName()).isEqualTo(authorName);
    }

    @Test
    public void should_return_bad_request_when_author_name_is_empty() throws Exception {
        // given
        var requestBody = """
              {
                  "authorName": ""
              }
              """;

        // when
        var resultActions = mockMvc.perform(post(BASE_ENDPOINT)
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestBody));

        // then
        resultActions
              .andExpect(status().isBadRequest());
    }
}
