package com.cgdpd.library.catalog.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.cgdpd.library.catalog.app.service.AuthorService;
import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.AuthorId;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;

import org.junit.jupiter.api.Test;

public class AuthorControllerTest {

    private final AuthorService authorService = mock(AuthorService.class);

    private final AuthorController authorController = new AuthorController(authorService);

    @Test
    void should_create_author() {
        // given
        var authorName = "John Doe";
        var id = AuthorId.of(1L);
        var createAuthorRequestDTO = new CreateAuthorRequestDto(authorName);

        var author = new Author(id, authorName);
        given(authorService.createAuthor(authorName)).willReturn(author);

        // when
        var responseCreatedAuthor = authorController.createAuthor(createAuthorRequestDTO);

        // then
        assertThat(responseCreatedAuthor).isNotNull();
        assertThat(responseCreatedAuthor.id()).isEqualTo(id);
        assertThat(responseCreatedAuthor.name()).isEqualTo(authorName);
    }
}
