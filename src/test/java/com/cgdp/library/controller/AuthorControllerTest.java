package com.cgdp.library.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cgdp.library.dto.author.AuthorDTO;
import com.cgdp.library.dto.author.CreateAuthorRequestDTO;
import com.cgdp.library.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

public class AuthorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void should_create_author() throws Exception {
        // given
        String authorName = "John Doe";
        Long id = 1L;
        CreateAuthorRequestDTO createAuthorRequestDTO = new CreateAuthorRequestDTO(authorName);

        AuthorDTO authorDTO = new AuthorDTO(id, authorName);
        given(authorService.createAuthor(authorName)).willReturn(authorDTO);

        // when
        ResultActions resultActions = mockMvc.perform(post("/authors")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(createAuthorRequestDTO)));

        // then
        resultActions
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.id", is(id.intValue())))
              .andExpect(jsonPath("$.authorName", is(authorName)));
    }

    @Test
    public void should_return_bad_request_when_author_name_is_empty() throws Exception {
        // given
        String authorName = "";
        CreateAuthorRequestDTO createAuthorRequestDTO = new CreateAuthorRequestDTO(authorName);

        given(authorService.createAuthor(authorName)).willThrow(new ResponseStatusException(
              HttpStatus.BAD_REQUEST));

        // when
        ResultActions resultActions = mockMvc.perform(post("/authors")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(createAuthorRequestDTO)));

        // then
        resultActions.andExpect(status().isBadRequest());
    }
}
