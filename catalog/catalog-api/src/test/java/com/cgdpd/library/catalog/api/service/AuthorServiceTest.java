package com.cgdpd.library.catalog.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


import com.cgdpd.library.catalog.api.entity.AuthorEntity;
import com.cgdpd.library.catalog.api.repository.AuthorRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AuthorServiceTest {

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Captor
    private ArgumentCaptor<AuthorEntity> captor;

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
