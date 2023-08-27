package com.cgdpd.library.service;

import static com.cgdpd.library.BookTestData.aCreateBookRequestDTO;
import static com.cgdpd.library.BookTestData.bookEntityFromRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.cgdpd.library.dto.book.BookDTO;
import com.cgdpd.library.dto.book.copy.SearchBookCriteria;
import com.cgdpd.library.entity.AuthorEntity;
import com.cgdpd.library.entity.BookEntity;
import com.cgdpd.library.exceptions.NotFoundException;
import com.cgdpd.library.mapper.BookMapper;
import com.cgdpd.library.mapper.BookMapperImpl;
import com.cgdpd.library.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

class BookServiceTest {

    private final BookMapper bookMapper = new BookMapperImpl();
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorService authorService;
    private BookService bookService;

    @Captor
    private ArgumentCaptor<BookEntity> captor;


    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
        this.bookService = new BookService(bookRepository, bookMapper, authorService);
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void should_create_a_book() {
        // given
        var request = aCreateBookRequestDTO().build();
        given(authorService.authorExist(request.authorId())).willReturn(true);
        var bookEntity = bookEntityFromRequest(request).build();
        given(bookRepository.save(captor.capture())).willReturn(bookEntity);

        // when
        var bookDTO = bookService.createBook(request);

        // then
        assertThat(bookDTO.id().value()).isEqualTo(bookEntity.getId());
        assertThat(bookDTO.title()).isEqualTo(request.title());
        assertThat(bookDTO.authorId()).isEqualTo(request.authorId());
        assertThat(bookDTO.publicationYear()).isEqualTo(request.publicationYear());
        assertThat(bookDTO.isbn()).isEqualTo(request.isbn());
        assertThat(bookDTO.genre()).isEqualTo(request.genre());
        verify(bookRepository).save(captor.getValue());
        assertThat(request.title()).isEqualTo(captor.getValue().getTitle());
        assertThat(bookEntity.getAuthorEntity()).isEqualTo(captor.getValue().getAuthorEntity());
        assertThat(request.publicationYear()).hasValue(captor.getValue().getPublicationYear());
        assertThat(request.isbn().value()).isEqualTo(captor.getValue().getIsbn());
        assertThat(request.genre()).isEqualTo(captor.getValue().getGenre());
    }

    @Test
    public void should_throw_exception_if_author_not_exist() {
        // given
        var request = aCreateBookRequestDTO().build();
        given(authorService.authorExist(request.authorId())).willReturn(false);

        // when then
        assertThatThrownBy(() -> bookService.createBook(request))
              .isInstanceOf(NotFoundException.class)
              .hasMessage(String.format("Author with id %s not found", request.authorId()));

        verifyNoInteractions(bookRepository);
    }

    @Test
    public void should_return_books_and_pages() {
        // given
        SearchBookCriteria criteria = SearchBookCriteria.builder()
              .bookTitle(Optional.of("The Great Gatsby"))
              .authorName(Optional.of("F. Scott Fitzgerald"))
              .genre(Optional.of("Fiction"))
              .publicationYearLessThan(Optional.of((short) 1926))
              .publicationYearGreaterThan(Optional.of((short) 1924))
              .build();

        int page = 0;
        int size = 10;
        Sort sort = Sort.by(Sort.Direction.ASC, "title");

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);
        bookEntity.setTitle("The Great Gatsby");
        bookEntity.setGenre("Fiction");
        bookEntity.setIsbn("9780134685991");
        bookEntity.setPublicationYear((short) 1925);

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName("F. Scott Fitzgerald");
        authorEntity.setId(1L);
        bookEntity.setAuthorEntity(authorEntity);

        Page<BookEntity> booksPage = new PageImpl<>(List.of(bookEntity));

        when(bookRepository.findAll(any(Specification.class), any(Pageable.class)))
              .thenReturn(booksPage);

        // when
        Page<BookDTO> result = bookService.getBooks(criteria, page, size, sort);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getContent()).hasSize(1);
    }
}
