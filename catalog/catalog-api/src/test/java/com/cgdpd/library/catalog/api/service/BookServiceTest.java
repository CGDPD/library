package com.cgdpd.library.catalog.api.service;

import static com.cgdpd.library.catalog.api.BookCopyEntityTestData.aBookCopyEntity;
import static com.cgdpd.library.catalog.api.BookEntityTestData.aBookEntity;
import static com.cgdpd.library.catalog.api.BookEntityTestData.bookEntityFromRequest;
import static com.cgdpd.library.catalog.domain.BookTestData.aCreateBookRequestDTO;
import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.AVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.cgdpd.library.catalog.api.entity.BookEntity;
import com.cgdpd.library.catalog.api.mapper.BookMapper;
import com.cgdpd.library.catalog.api.mapper.BookMapperImpl;
import com.cgdpd.library.catalog.api.repository.BookRepository;
import com.cgdpd.library.common.exception.NotFoundException;
import com.cgdpd.library.types.Isbn13;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

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
        var book = bookService.createBook(request);

        // then
        assertThat(book.id().value()).isEqualTo(bookEntity.getId());
        assertThat(book.title()).isEqualTo(request.title());
        assertThat(book.authorId()).isEqualTo(request.authorId());
        assertThat(book.publicationYear()).isEqualTo(request.publicationYear());
        assertThat(book.isbn()).isEqualTo(request.isbn());
        assertThat(book.genre()).isEqualTo(request.genre());
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
    void should_find_book_by_isbn13() {
        // given
        var bookId = 1L;
        var bookEntity = aBookEntity()
              .id(bookId)
              .bookCopyEntities(List.of(
                    aBookCopyEntity(bookId)
                          .trackingStatus(AVAILABLE.name())
                          .build()))
              .build();

        var isbn13 = bookEntity.getIsbn();
        given(bookRepository.findDetailedBookByIsbn(isbn13))
              .willReturn(Optional.of(bookEntity));

        // when
        var result = bookService.findDetailedBookByIsbn13(Isbn13.of(isbn13));

        // then
        var expectedDetailedBookDto = bookMapper.mapToDetailedBookDto(bookEntity);
        assertThat(result).hasValue(expectedDetailedBookDto);
    }
}
