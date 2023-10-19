package com.cgdpd.library.catalog.app.service;

import static com.cgdpd.library.catalog.app.BookCopyEntityTestData.aBookCopyEntity;
import static com.cgdpd.library.catalog.app.BookEntityTestData.aBookEntity;
import static com.cgdpd.library.catalog.app.BookEntityTestData.bookEntityFromRequest;
import static com.cgdpd.library.catalog.domain.BookTestData.aCreateBookRequestDto;
import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.AVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.cgdpd.library.catalog.app.entity.BookEntity;
import com.cgdpd.library.catalog.app.mapper.BookMapper;
import com.cgdpd.library.catalog.app.mapper.BookMapperImpl;
import com.cgdpd.library.catalog.app.repository.BookRepository;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.dto.SearchBookCriteria;
import com.cgdpd.library.common.exception.NotFoundException;
import com.cgdpd.library.common.pagination.PaginationCriteria;
import com.cgdpd.library.common.pagination.SortParams;
import com.cgdpd.library.common.type.Isbn13;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

class BookServiceTest {

    private final BookMapper bookMapper = new BookMapperImpl();
    private final BookRepository bookRepository = mock(BookRepository.class);
    private final AuthorService authorService = mock(AuthorService.class);
    private final BookService bookService =
          new BookService(bookRepository, bookMapper, authorService);

    private final ArgumentCaptor<BookEntity> captor = ArgumentCaptor.forClass(BookEntity.class);


    @Test
    public void should_create_a_book() {
        // given
        var request = aCreateBookRequestDto().build();
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
        var request = aCreateBookRequestDto().build();
        given(authorService.authorExist(request.authorId())).willReturn(false);

        // when then
        assertThatThrownBy(() -> bookService.createBook(request))
              .isInstanceOf(NotFoundException.class)
              .hasMessage(String.format("Author with id %s not found", request.authorId()));

        verifyNoInteractions(bookRepository);
    }

    @Test
    void should_return_books_with_pagination() {
        // given
        var searchCriteria = SearchBookCriteria.builder().build();
        var paginationCriteria = PaginationCriteria.builder()
              .pageIndex(0)
              .pageSize(10)
              .sort(Optional.of("title"))
              .direction(Optional.of(SortParams.Direction.ASC.name()))
              .build();
        var books = List.of(aBookEntity().id(1L).title("The Adventurous").build(),
              aBookEntity().id(2L).title("Finder").build(),
              aBookEntity().id(3L).title("Killer").build());

        given(bookRepository.findAll(any(Specification.class), any(PageRequest.class)))
              .willReturn(new PageImpl<>(books));

        // when
        var result = bookService.findDetailedBooksPage(paginationCriteria, searchCriteria);

        // then
        assertThat(result.content()).containsExactlyInAnyOrder(books.stream()
              .map(bookMapper::mapToDetailedBookDto)
              .toArray(DetailedBookDto[]::new));
    }

    @Test
    void should_return_empty_page_when_no_books_found() {
        // given
        var searchCriteria = SearchBookCriteria.builder().build();
        var paginationCriteria = PaginationCriteria.builder()
              .pageIndex(0)
              .pageSize(10)
              .build();
        var emptyPage = Page.empty();

        given(bookRepository.findAll(any(Specification.class), any(PageRequest.class)))
              .willReturn(emptyPage);

        // when
        var resultPagedResponse = bookService.findDetailedBooksPage(paginationCriteria,
              searchCriteria);

        // then
        assertThat(resultPagedResponse.content()).isEmpty();
        assertThat(resultPagedResponse.pageNumber()).isEqualTo(emptyPage.getNumber());
        assertThat(resultPagedResponse.pageSize()).isEqualTo(emptyPage.getSize());
        assertThat(resultPagedResponse.totalElements()).isEqualTo(emptyPage.getTotalElements());
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
