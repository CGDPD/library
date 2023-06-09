package com.cgdp.library.controller;


import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cgdp.library.FunctionalTest;
import com.cgdp.library.dto.author.CreateAuthorRequestDTO;
import com.cgdp.library.entity.AuthorEntity;
import com.cgdp.library.repository.AuthorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

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
        String authorName = "John Doe";
        CreateAuthorRequestDTO createAuthorRequestDTO = new CreateAuthorRequestDTO(authorName);

        // when
        ResultActions resultActions = mockMvc.perform(post("/authors")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(createAuthorRequestDTO)));
        // then
        resultActions
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id", is(Long.class)).exists())
              .andExpect(jsonPath("$.name", is(authorName)).exists());

        Long id = getIdFromResult(resultActions);
        Optional<AuthorEntity> authorEntity = authorRepository.findById(id);
        assertThat(authorEntity).isPresent();
        assertThat(authorEntity.get().getName()).isEqualTo(authorName);
    }


    @Test
    public void should_return_bad_request_when_author_name_is_empty() throws Exception {
        // given
        String empty = "";
        CreateAuthorRequestDTO createAuthorRequestDTO = new CreateAuthorRequestDTO(empty);

        // when
        ResultActions resultActions = mockMvc.perform(post("/authors")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(createAuthorRequestDTO)));

        // then
        resultActions
              .andExpect(status().isBadRequest());
    }

    private Long getIdFromResult(ResultActions resultActions)
          throws UnsupportedEncodingException, JSONException {
        MvcResult mvcResult = resultActions.andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(jsonResponse);
        return jsonObject.getLong("id");
    }
}
