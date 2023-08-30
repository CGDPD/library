package com.cgdpd.library.service;

import static com.cgdpd.library.BookTestData.JANE_DANE__KILLER__2001;
import static com.cgdpd.library.BookTestData.JOHN_DOE__FINDER__1995;
import static com.cgdpd.library.BookTestData.JOHN_DOE__THE_ADVENTUROUS__1987;
import static com.cgdpd.library.BookTestData.aCreateBookRequestDTO;
import static com.cgdpd.library.BookTestData.bookEntityFromRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.cgdpd.library.dto.book.SearchBookCriteria;
import com.cgdpd.library.dto.pagination.PagedResponse;
import com.cgdpd.library.dto.pagination.PaginationCriteria;
import com.cgdpd.library.dto.pagination.SortParam;
import com.cgdpd.library.entity.BookEntity;
import com.cgdpd.library.exceptions.NotFoundException;
import com.cgdpd.library.mapper.BookMapper;
import com.cgdpd.library.mapper.BookMapperImpl;
import com.cgdpd.library.model.book.Book;
import com.cgdpd.library.repository.BookRepository;
import com.cgdpd.library.repository.specification.BookSpecifications;
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
import org.springframework.data.domain.PageRequest;
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
    void should_create_a_book() {
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
    void should_throw_exception_if_author_not_exist() {
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
    void should_return_books_with_pagination() {
        // given
        var searchCriteria = SearchBookCriteria.builder().build();
        Specification<BookEntity> spec = BookSpecifications.byBookSearchCriteria(searchCriteria);
        int pageNumber = 0;
        int pageSize = 10;
        var sortParam = new SortParam("title", SortParam.Order.ASC);
        var paginationCriteria = new PaginationCriteria(pageNumber, pageSize,
              Optional.of(sortParam));
        Sort sort = sortParam.toSort();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        when(bookRepository.findAll(spec, pageable))
              .thenReturn(new PageImpl<>(List.of(
                    JOHN_DOE__THE_ADVENTUROUS__1987,
                    JOHN_DOE__FINDER__1995,
                    JANE_DANE__KILLER__2001
              )));

        // when
        PagedResponse<Book> result = bookService.getBooks(paginationCriteria, searchCriteria);

        // then
        assertThat(result.getContent().size()).isEqualTo(3);
        assertThat(result.getContent().get(0).title()).isEqualTo("Expected Title");
    }

    @Test
    void should_return_empty_page_when_no_books_found() {
        // given
        var searchCriteria = SearchBookCriteria.builder().build();
        Specification<BookEntity> spec = BookSpecifications.byBookSearchCriteria(searchCriteria);
        int page = 0;
        int size = 10;
        var sortParam = new SortParam("title", SortParam.Order.ASC);
        var paginationCriteria = new PaginationCriteria(page, size, Optional.of(sortParam));
        Sort sort = sortParam.toSort();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<BookEntity> emptyPage = Page.empty();

        when(bookRepository.findAll(spec, pageable)).thenReturn(emptyPage);

        // when
        PagedResponse<Book> result = bookService.getBooks(paginationCriteria, searchCriteria);

        // then
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getPageNumber()).isEqualTo(page);
        assertThat(result.getPageSize()).isEqualTo(size);
        assertThat(result.getTotalElements()).isEqualTo(emptyPage.getTotalElements());
    }

    @Test
    void should_return_last_page() {
        // given
        var searchCriteria = SearchBookCriteria.builder().build();
        Specification<BookEntity> spec = BookSpecifications.byBookSearchCriteria(searchCriteria);
        int page = 5;
        int size = 10;
        var sortParam = new SortParam("title", SortParam.Order.ASC);
        var paginationCriteria = new PaginationCriteria(page, size, Optional.of(sortParam));
        Sort sort = sortParam.toSort();
        Pageable pageable = PageRequest.of(page, size, sort);

        when(bookRepository.findAll(spec, pageable))
              .thenReturn(new PageImpl<>(List.of(), Pageable.unpaged(), 0));

        // when
        PagedResponse<Book> result = bookService.getBooks(paginationCriteria, searchCriteria);

        // then
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getPageNumber()).isEqualTo(page);
        assertThat(result.getPageSize()).isEqualTo(size);
    }

    @Test
    void should_throw_exception_for_invalid_input_page() {
        // given
        var searchCriteria = SearchBookCriteria.builder().build();
        int invalidPage = -1;
        int size = 10;
        var sortParam = new SortParam("title", SortParam.Order.ASC);
        var paginationCriteria = new PaginationCriteria(invalidPage, size, Optional.of(sortParam));

        // when then
        assertThatThrownBy(() -> bookService.getBooks(paginationCriteria, searchCriteria))
              .isInstanceOf(IllegalArgumentException.class)
              .hasMessageContaining("Page index must not be less than zero");

        verifyNoInteractions(bookRepository);
    }
}
