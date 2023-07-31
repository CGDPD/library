package com.cgdpd.library.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.cgdpd.library.dto.author.AuthorDTO;
import com.cgdpd.library.dto.author.CreateAuthorRequestDTO;
import com.cgdpd.library.dto.author.CreateAuthorResponseDTO;
import com.cgdpd.library.service.AuthorService;
import com.cgdpd.library.type.AuthorId;
import org.junit.jupiter.api.AfterEach;
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
    void should_create_author() {
        // given
        String authorName = "John Doe";
        AuthorId id = AuthorId.of(1L);
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
