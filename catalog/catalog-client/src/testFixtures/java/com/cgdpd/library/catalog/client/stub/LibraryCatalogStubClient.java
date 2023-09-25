package com.cgdpd.library.catalog.client.stub;

import static com.cgdpd.library.common.validation.Validator.required;

import com.cgdpd.library.catalog.client.LibraryCatalogClient;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.dto.SearchBookCriteria;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.catalog.domain.book.model.BookId;
import com.cgdpd.library.catalog.domain.book.model.copy.BookCopy;
import com.cgdpd.library.common.exception.NotFoundException;
import com.cgdpd.library.common.pagination.PaginationCriteria;
import com.cgdpd.library.common.type.Isbn13;

import reactor.core.publisher.Flux;
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
    public Flux<DetailedBookDto> searchBooks(PaginationCriteria paginationCriteria,
                                             SearchBookCriteria searchBookCriteria) {
        return Flux.fromIterable(inMemoryDb.books().values().stream()
              .filter(searchBookCriteria::matches)
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
              .collect(Collectors.toList()));
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
}
