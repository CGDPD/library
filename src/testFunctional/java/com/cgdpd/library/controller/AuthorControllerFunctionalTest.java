package com.cgdpd.library.controller;


import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cgdpd.library.FunctionalTest;
import com.cgdpd.library.dto.author.CreateAuthorRequestDTO;
import com.cgdpd.library.entity.AuthorEntity;
import com.cgdpd.library.repository.AuthorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
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
public class AuthorControllerFunctionalTest extends FunctionalTest {

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
        var createAuthorRequestDTO = new CreateAuthorRequestDTO(authorName);

        // when
        var resultActions = mockMvc.perform(post("/authors")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(createAuthorRequestDTO)));
        // then
        resultActions
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id", is(Long.class)).exists())
              .andExpect(jsonPath("$.authorName", is(authorName)).exists());

        var id = getIdFromResult(resultActions);
        var authorEntity = authorRepository.findById(id);
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
        var resultActions = mockMvc.perform(post("/authors")
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestBody));

        // then
        resultActions
              .andExpect(status().isBadRequest());
    }

    private Long getIdFromResult(ResultActions resultActions)
          throws UnsupportedEncodingException, JSONException {
        var mvcResult = resultActions.andReturn();
        var jsonResponse = mvcResult.getResponse().getContentAsString();
        var jsonObject = new JSONObject(jsonResponse);
        return jsonObject.getLong("id");
    }
}
