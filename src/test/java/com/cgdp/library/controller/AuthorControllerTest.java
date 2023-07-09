package com.cgdp.library.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.cgdp.library.dto.author.AuthorDTO;
import com.cgdp.library.dto.author.CreateAuthorRequestDTO;
import com.cgdp.library.dto.author.CreateAuthorResponseDTO;
import com.cgdp.library.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_create_author() {
        // given
        String authorName = "John Doe";
        Long id = 1L;
        CreateAuthorRequestDTO createAuthorRequestDTO = new CreateAuthorRequestDTO(authorName);

        AuthorDTO authorDTO = new AuthorDTO(id, authorName);
        given(authorService.createAuthor(authorName)).willReturn(authorDTO);

        // when
        CreateAuthorResponseDTO responseDTO = authorController.createAuthor(createAuthorRequestDTO);

        // then
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.id()).isEqualTo(id);
        assertThat(responseDTO.authorName()).isEqualTo(authorName);
    }
}
