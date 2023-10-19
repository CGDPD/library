package com.cgdpd.library.catalog.client.stub;

import static com.cgdpd.library.common.validation.Validator.required;

import com.cgdpd.library.catalog.client.LibraryCatalogClient;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.dto.SearchBookCriteria;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.catalog.domain.book.model.BookId;
import com.cgdpd.library.catalog.domain.book.model.copy.BookCopy;
import com.cgdpd.library.common.exception.NotFoundException;
import com.cgdpd.library.common.pagination.PagedResponse;
import com.cgdpd.library.common.pagination.PaginationCriteria;
import com.cgdpd.library.common.type.Isbn13;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public class LibraryCatalogStubClient implements LibraryCatalogClient {

    private final CatalogInMemoryDb inMemoryDb;

    public LibraryCatalogStubClient(CatalogInMemoryDb inMemoryDb) {
        this.inMemoryDb = required("inMemoryDb", inMemoryDb);
    }

    @Override
    public Mono<DetailedBookDto> getDetailedBookDto(Isbn13 isbn13) {
        return Mono.fromSupplier(() -> {
            var book = getBookByIsbn13(isbn13);
            var author = inMemoryDb.authors().get(book.authorId());
            var bookCopies = findBookCopiedByBookId(book.id());
            var trackingStatusList = bookCopies.stream().map(BookCopy::trackingStatus).toList();

            return DetailedBookDto.builder()
                  .id(book.id())
                  .title(book.title())
                  .authorId(author.id())
                  .authorName(author.name())
                  .isbn(book.isbn())
                  .genre(book.genre())
                  .trackingStatusList(trackingStatusList)
                  .publicationYear(book.publicationYear())
                  .build();
        });
    }

    @Override
    public Mono<PagedResponse<DetailedBookDto>> getBooksByCriteria(
          PaginationCriteria paginationCriteria,
          SearchBookCriteria searchBookCriteria) {
        return Mono.defer(() -> {
            var detailedBooks = inMemoryDb.books().values().stream()
                  .filter(book -> matches(book, searchBookCriteria))
                  .skip((long) paginationCriteria.pageIndex() * paginationCriteria.pageSize())
                  .limit(paginationCriteria.pageSize())
                  .map(book -> {
                      var author = inMemoryDb.authors().get(book.authorId());
                      var bookCopies = findBookCopiedByBookId(book.id());
                      var trackingStatusList = bookCopies.stream().map(BookCopy::trackingStatus)
                            .toList();

                      return DetailedBookDto.builder()
                            .id(book.id())
                            .title(book.title())
                            .authorId(author.id())
                            .authorName(author.name())
                            .isbn(book.isbn())
                            .genre(book.genre())
                            .trackingStatusList(trackingStatusList)
                            .publicationYear(book.publicationYear())
                            .build();
                  })
                  .collect(Collectors.toList());

            var pagedResponse = PagedResponse.<DetailedBookDto>builder()
                  .content(detailedBooks)
                  .pageNumber(paginationCriteria.pageIndex())
                  .pageSize(paginationCriteria.pageSize())
                  .totalElements(detailedBooks.size())
                  .totalPages(1)
                  .build();

            return Mono.just(pagedResponse);
        });
    }

    private Book getBookByIsbn13(Isbn13 isbn13) {
        return inMemoryDb.books().values().stream()
              .filter(book -> book.isbn().equals(isbn13))
              .findFirst()
              .orElseThrow(() -> new NotFoundException(
                    String.format("No book by the isbn %s", isbn13.value())));
    }

    private List<BookCopy> findBookCopiedByBookId(BookId bookId) {
        return inMemoryDb.bookCopies().values().stream()
              .filter(bookCopy -> bookCopy.bookId().equals(bookId))
              .toList();
    }

    public boolean matches(Book book, SearchBookCriteria searchCriteria) {
        var authorName = inMemoryDb.authors().get(book.authorId()).name();

        return searchCriteria.bookTitle().map(title -> title.equals(book.title())).orElse(true)
              && (searchCriteria.authorName().isEmpty() || searchCriteria.authorName().get()
              .equals(authorName))
              && searchCriteria.genre().map(genre -> genre.equals(book.genre())).orElse(true)
              && searchCriteria.publicationYearLessThan().map(
              year -> year > (book.publicationYear().isPresent() ? book.publicationYear().get()
                    : (short) 0)).orElse(true)
              && searchCriteria.publicationYearGreaterThan().map(
              year -> year < (book.publicationYear().isPresent() ? book.publicationYear().get()
                    : Short.MAX_VALUE)).orElse(true);
    }
}
