package com.cgdpd.library.catalog.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.cgdpd.library.catalog.app.entity.AuthorEntity;
import com.cgdpd.library.catalog.app.repository.AuthorRepository;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class AuthorServiceTest {

    private final AuthorRepository authorRepository = Mockito.mock(AuthorRepository.class);
    private final AuthorService authorService = new AuthorService(authorRepository);

    private final ArgumentCaptor<AuthorEntity> captor = ArgumentCaptor.forClass(AuthorEntity.class);

    @Test
    public void should_create_and_return_author() {
        // given
        var authorName = "John Doe";
        Long id = 1L;
        var authorEntity = new AuthorEntity(id, authorName);
        given(authorRepository.save(captor.capture())).willReturn(authorEntity);

        // when
        var author = authorService.createAuthor(authorName);

        // then
        assertThat(author.id().value()).isEqualTo(id);
        assertThat(author.name()).isEqualTo(authorName);
        verify(authorRepository).save(captor.getValue());
        assertThat(authorName).isEqualTo(captor.getValue().getName());
    }
}
