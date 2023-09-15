package com.cgdpd.library.catalog.client.stub;

import com.cgdpd.library.catalog.client.LibraryCatalogClient;
import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.AuthorId;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.catalog.domain.book.model.BookId;
import com.cgdpd.library.catalog.domain.book.model.copy.BookCopy;
import com.cgdpd.library.catalog.domain.book.model.copy.BookCopyId;
import com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus;
import com.cgdpd.library.catalog.domain.book.model.copy.UserId;
import com.cgdpd.library.common.client.StubClient;
import com.cgdpd.library.common.exception.NotFoundException;
import com.cgdpd.library.types.Isbn13;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class LibraryCatalogStubClient implements LibraryCatalogClient, StubClient {

    private static final Long INITIAL_VALUE = 1L;

    private static final AtomicLong authorIdGenerator = new AtomicLong(INITIAL_VALUE);
    private static final AtomicLong bookIdGenerator = new AtomicLong(INITIAL_VALUE);
    private static final AtomicLong bookCopyIdGenerator = new AtomicLong(INITIAL_VALUE);

    private static final Faker FAKER = Faker.instance();


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

    public List<AuthorBooks> generateAuthorsAndBooks(int authorQuantity,
                                                     int bookQuantity,
                                                     int bookCopyQuantity) {

        var authorsBooks = new ArrayList<AuthorBooks>();

        for (var i = 0; i < authorQuantity; i++) {
            authorsBooks.add(generateAuthorAndBooks(bookQuantity, bookCopyQuantity));
        }
        return authorsBooks;
    }

    public AuthorBooks generateAuthorAndBooks(int bookQuantity, int bookCopyQuantity) {

        var createdAuthor = createAuthor(new CreateAuthorRequestDto(FAKER.book().author()));
        var createdBooks = new HashMap<Book, List<BookCopy>>();

        for (var i = 0; i < bookQuantity; i++) {
            var createdBook = createBook(generateCreateBookRequest(createdAuthor.id()));
            var bookCopies = new ArrayList<BookCopy>();
            for (var j = 0; j < bookCopyQuantity; j++) {
                var createdBookCopy = generateBookCopy(createdBook.id());
                bookCopies.add(createdBookCopy);
            }
            createdBooks.put(createdBook, bookCopies);
        }

        return new AuthorBooks(createdAuthor, createdBooks);
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

    private CreateBookRequestDto generateCreateBookRequest(AuthorId authorId) {
        var rnd = new Random();

        return CreateBookRequestDto.builder()
              .title(FAKER.book().title())
              .authorId(authorId)
              .isbn(Isbn13.random())
              .genre(FAKER.book().genre())
              .publicationYear(Optional.of(
                    (short) rnd.nextInt(
                          LocalDate.now().minusYears(1000).getYear(),
                          LocalDate.now().getYear())))
              .build();
    }

    private BookCopy generateBookCopy(BookId bookId) {
        var rnd = new Random();
        var trackingStatusValues = TrackingStatus.values();
        var randomTrackingStatus = trackingStatusValues[rnd.nextInt(trackingStatusValues.length)];

        var userId = Optional.<UserId>empty();

        if (randomTrackingStatus.requiresUser()
              || (randomTrackingStatus.optionalUser() && rnd.nextBoolean())) {
            userId = Optional.of(UserId.of(rnd.nextLong()));
        }

        var bookCopy = BookCopy.builder()
              .id(BookCopyId.of(bookCopyIdGenerator.getAndIncrement()))
              .bookId(bookId)
              .trackingStatus(randomTrackingStatus)
              .userId(userId)
              .build();

        addBookCopy(bookCopy);
        return bookCopy;
    }

    public record AuthorBooks(Author author,
                              Map<Book, List<BookCopy>> books) {
    }
}
