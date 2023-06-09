package com.cgdp.library.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.cgdp.library.dto.author.AuthorDTO;
import com.cgdp.library.entity.AuthorEntity;
import com.cgdp.library.repository.AuthorRepository;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_create_and_return_author() {
        // given
        String authorName = "John Doe";
        Long id = 1L;
        AuthorEntity authorEntity = new AuthorEntity(id, authorName);
        given(authorRepository.save(captor.capture())).willReturn(authorEntity);

        // when
        AuthorDTO authorDTO = authorService.createAuthor(authorName);

        // then
        assertThat(authorDTO.id()).isEqualTo(id);
        assertThat(authorDTO.name()).isEqualTo(authorName);
        verify(authorRepository).save(captor.getValue());
        assertThat(authorName).isEqualTo(captor.getValue().getName());
    }
}
