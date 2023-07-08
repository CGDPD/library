package com.cgdp.library.service;

import static com.cgdpd.library.BookTestData.aCreateBookRequestDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.cgdp.library.dto.book.BookDTO;
import com.cgdp.library.dto.book.CreateBookRequestDTO;
import com.cgdp.library.entity.AuthorEntity;
import com.cgdp.library.entity.BookEntity;
import com.cgdp.library.exceptions.NotFoundException;
import com.cgdp.library.mapper.BookMapper;
import com.cgdp.library.mapper.BookMapperImpl;
import com.cgdp.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BookServiceTest {

    private final BookMapper bookMapper = new BookMapperImpl();
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorService authorService;
    private BookService bookService;

    @Captor
    private ArgumentCaptor<BookEntity> captor;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.bookService = new BookService(bookRepository, bookMapper, authorService);
    }

    @Test
    public void should_create_a_book() {
        // given
        CreateBookRequestDTO request = aCreateBookRequestDTO().build();
        given(authorService.authorExist(request.authorId())).willReturn(true);
        BookEntity bookEntity = bookEntityFromRequest(request);
        given(bookRepository.save(captor.capture())).willReturn(bookEntity);

        // when
        BookDTO bookDTO = bookService.createBook(request);

        // then
        assertThat(bookDTO.id()).isEqualTo(bookEntity.getId());
        assertThat(bookDTO.title()).isEqualTo(request.title());
        assertThat(bookDTO.authorId()).isEqualTo(request.authorId());
        assertThat(bookDTO.publicationYear().orElse(null)).isEqualTo(
              request.publicationYear().orElse(null));
        assertThat(bookDTO.isbn()).isEqualTo(request.isbn());
        assertThat(bookDTO.genre()).isEqualTo(request.genre());
        verify(bookRepository).save(captor.getValue());
        assertThat(request.title()).isEqualTo(captor.getValue().getTitle());
        assertThat(bookEntity.getAuthorEntity()).isEqualTo(captor.getValue().getAuthorEntity());
        assertThat(request.publicationYear().orElse(null)).isEqualTo(
              captor.getValue().getPublicationYear());
        assertThat(request.isbn()).isEqualTo(captor.getValue().getIsbn());
        assertThat(request.genre()).isEqualTo(captor.getValue().getGenre());
    }

    @Test
    public void should_throw_exception_if_author_not_exist() {
        // given
        CreateBookRequestDTO request = aCreateBookRequestDTO().build();
        given(authorService.authorExist(request.authorId())).willReturn(false);

        // when then
        assertThatThrownBy(() -> bookService.createBook(request))
              .isInstanceOf(NotFoundException.class)
              .hasMessage(String.format("Author with id %s not found", request.authorId()));

        verifyNoInteractions(bookRepository);
    }

    private BookEntity bookEntityFromRequest(CreateBookRequestDTO request) {
        return BookEntity.builder()
              .id(1L)
              .title(request.title())
              .authorEntity(AuthorEntity.builder().id(request.authorId()).build())
              .publicationYear(request.publicationYear().orElse(null))
              .isbn(request.isbn())
              .genre(request.genre())
              .build();
    }
}
