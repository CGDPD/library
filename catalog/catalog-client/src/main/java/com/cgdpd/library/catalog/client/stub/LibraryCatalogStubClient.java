package com.cgdpd.library.catalog.client.stub;

import com.cgdpd.library.catalog.client.LibraryCatalogClient;
import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.AuthorId;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.BookAvailability;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.catalog.domain.book.model.BookId;
import com.cgdpd.library.catalog.domain.book.model.copy.BookCopy;
import com.cgdpd.library.catalog.domain.book.model.copy.BookCopyId;
import com.cgdpd.library.common.client.StubClient;
import com.cgdpd.library.common.exception.NotFoundException;
import com.cgdpd.library.types.Isbn13;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class LibraryCatalogStubClient implements LibraryCatalogClient, StubClient {

    private static final Long INITIAL_VALUE = 1L;

    private static final AtomicLong authorIdGenerator = new AtomicLong(INITIAL_VALUE);
    private static final AtomicLong bookIdGenerator = new AtomicLong(INITIAL_VALUE);
    private static final AtomicLong bookCopyIdGenerator = new AtomicLong(INITIAL_VALUE);


    private final Map<AuthorId, Author> authors;
    private final Map<BookId, Book> books;
    private final Map<BookCopyId, BookCopy> bookCopies;

    public LibraryCatalogStubClient() {
        this.authors = new HashMap<>();
        this.books = new HashMap<>();
        this.bookCopies = new HashMap<>();
    }

    @Override
    public Author createAuthor(CreateAuthorRequestDto createAuthorRequestDto) {
        var authorId = AuthorId.of(authorIdGenerator.getAndIncrement());
        var createdAuthor = Author.builder()
              .id(authorId)
              .name(createAuthorRequestDto.authorName())
              .build();
        authors.put(authorId, createdAuthor);
        return createdAuthor;
    }

    @Override
    public Book createBook(CreateBookRequestDto createBookRequestDto) {
        if (!authors.containsKey(createBookRequestDto.authorId())) {
            throw new NotFoundException(
                  "Author with id " + createBookRequestDto.authorId() + " not found");
        }
        var bookId = BookId.of(bookIdGenerator.getAndIncrement());
        var createdBook = Book.builder()
              .id(bookId)
              .title(createBookRequestDto.title())
              .authorId(createBookRequestDto.authorId())
              .isbn(createBookRequestDto.isbn())
              .genre(createBookRequestDto.genre())
              .publicationYear(createBookRequestDto.publicationYear())
              .build();
        books.put(bookId, createdBook);
        return createdBook;
    }

    @Override
    public DetailedBookDto getDetailedBookDto(Isbn13 isbn13) {
        var book = getBookByIsbn13(isbn13);
        var author = authors.get(book.authorId());
        var bookCopies = findBookCopiedByBookId(book.id());
        var trackingStatues = bookCopies.stream().map(BookCopy::trackingStatus).toList();
        var availability = BookAvailability.fromTrackingStatuses(trackingStatues);

        return DetailedBookDto.builder()
              .id(book.id())
              .title(book.title())
              .authorId(author.id())
              .authorName(author.name())
              .isbn(book.isbn())
              .genre(book.genre())
              .availability(availability)
              .publicationYear(book.publicationYear())
              .build();
    }

    // TODO: 07/09/2023 should be replaced when we create endpoint
    public void addBookCopy(BookCopy bookCopy) {
        bookCopies.put(bookCopy.id(), bookCopy);
    }

    public Map<AuthorId, Author> authors() {
        return Map.copyOf(authors);
    }

    public Map<BookId, Book> books() {
        return Map.copyOf(books);
    }

    public Map<BookCopyId, BookCopy> bookCopies() {
        return Map.copyOf(bookCopies);
    }

    @Override
    public void clear() {
        authors.clear();
        books.clear();
        bookCopies.clear();

        authorIdGenerator.set(INITIAL_VALUE);
        bookIdGenerator.set(INITIAL_VALUE);
        bookCopyIdGenerator.set(INITIAL_VALUE);
    }

    private Book getBookByIsbn13(Isbn13 isbn13) {
        return books.values().stream()
              .filter(book -> book.isbn().equals(isbn13))
              .findFirst()
              .orElseThrow(() -> new NotFoundException(
                    String.format("No book by the isbn %s", isbn13.value())));
    }

    private List<BookCopy> findBookCopiedByBookId(BookId bookId) {
        return bookCopies.values().stream()
              .filter(bookCopy -> bookCopy.bookId().equals(bookId))
              .toList();
    }
}
