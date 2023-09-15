package com.cgdpd.library.catalog.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cgdpd.library.catalog.app.FunctionalTest;
import com.cgdpd.library.catalog.app.repository.AuthorRepository;
import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public class AuthorControllerFunctionalTest extends FunctionalTest {

    private static final String BASE_ENDPOINT = "/author";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void should_create_author_and_return_name() {
        // given
        var authorName = "John Doe";
        var createAuthorRequestDto = new CreateAuthorRequestDto(authorName);

        // when
        var responseEntity = restTemplate.postForEntity(
              BASE_ENDPOINT,
              createAuthorRequestDto,
              Author.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);
        assertThat(responseEntity.hasBody()).isTrue();

        var responseBody = responseEntity.getBody();
        assertThat(responseBody.name()).isEqualTo(authorName);

        var authorEntity = authorRepository.findById(responseBody.id().value());
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
