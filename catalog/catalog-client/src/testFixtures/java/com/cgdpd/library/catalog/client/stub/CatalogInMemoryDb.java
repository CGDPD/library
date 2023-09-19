package com.cgdpd.library.catalog.client.stub;

import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.AuthorId;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.catalog.domain.book.model.BookId;
import com.cgdpd.library.catalog.domain.book.model.copy.BookCopy;
import com.cgdpd.library.catalog.domain.book.model.copy.BookCopyId;
import com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus;
import com.cgdpd.library.catalog.domain.book.model.copy.UserId;
import com.cgdpd.library.common.client.InMemoryDb;
import com.cgdpd.library.common.type.Isbn13;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class CatalogInMemoryDb implements InMemoryDb {

    private static final Long INITIAL_VALUE = 1L;

    private static final AtomicLong authorIdGenerator = new AtomicLong(INITIAL_VALUE);
    private static final AtomicLong bookIdGenerator = new AtomicLong(INITIAL_VALUE);
    private static final AtomicLong bookCopyIdGenerator = new AtomicLong(INITIAL_VALUE);

    private static final Faker FAKER = Faker.instance();


    private final Map<AuthorId, Author> authors = new HashMap<>();
    private final Map<BookId, Book> books = new HashMap<>();
    private final Map<BookCopyId, BookCopy> bookCopies = new HashMap<>();


    public List<AuthorBooks> generateAuthorsAndBooks(int authorQuantity,
                                                     int bookQuantity,
                                                     int bookCopyQuantity) {

        var authorsBooks = new ArrayList<AuthorBooks>();

        for (var i = 0; i < authorQuantity; i++) {
            authorsBooks.add(generateAuthorAndBooks(bookQuantity, bookCopyQuantity));
        }
        return authorsBooks;
    }

    public Author addAuthor(Author.AuthorBuilder authorBuilder) {
        var author = authorBuilder.id(AuthorId.of(authorIdGenerator.getAndIncrement())).build();
        authors.put(author.id(), author);
        return author;
    }

    public Book addBook(Book.BookBuilder bookBuilder) {
        var book = bookBuilder.id(BookId.of(bookIdGenerator.getAndIncrement())).build();
        books.put(book.id(), book);
        return book;
    }

    public BookCopy addBookCopy(BookCopy.BookCopyBuilder bookCopyBuilder) {
        var bookCopy = bookCopyBuilder.id(BookCopyId.of(bookCopyIdGenerator.getAndIncrement()))
              .build();
        bookCopies.put(bookCopy.id(), bookCopy);
        return bookCopy;
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

    public AuthorBooks generateAuthorAndBooks(int bookQuantity,
                                              int bookCopyQuantity) {

        var createdAuthor = addAuthor(Author.builder().name(FAKER.book().author()));
        var createdBooks = new HashMap<Book, List<BookCopy>>();

        for (var i = 0; i < bookQuantity; i++) {
            var createdBook = addBook(generateBook(createdAuthor.id()));
            var bookCopies = new ArrayList<BookCopy>();
            for (var j = 0; j < bookCopyQuantity; j++) {
                var createdBookCopy = addBookCopy(generateBookCopy(createdBook.id()));
                bookCopies.add(createdBookCopy);
            }
            createdBooks.put(createdBook, bookCopies);
        }

        return new AuthorBooks(createdAuthor, createdBooks);
    }


    private Book.BookBuilder generateBook(AuthorId authorId) {
        var rnd = new Random();

        return Book.builder()
              .title(FAKER.book().title())
              .authorId(authorId)
              .isbn(Isbn13.random())
              .genre(FAKER.book().genre())
              .publicationYear(Optional.of(
                    (short) rnd.nextInt(
                          LocalDate.now().minusYears(1000).getYear(),
                          LocalDate.now().getYear())));
    }

    private BookCopy.BookCopyBuilder generateBookCopy(BookId bookId) {
        var rnd = new Random();
        var trackingStatusValues = TrackingStatus.values();
        var randomTrackingStatus = trackingStatusValues[rnd.nextInt(trackingStatusValues.length)];

        var userId = Optional.<UserId>empty();

        if (randomTrackingStatus.requiresUser()
              || (randomTrackingStatus.optionalUser() && rnd.nextBoolean())) {
            userId = Optional.of(UserId.of(rnd.nextLong()));
        }

        return BookCopy.builder()
              .id(BookCopyId.of(bookCopyIdGenerator.getAndIncrement()))
              .bookId(bookId)
              .trackingStatus(randomTrackingStatus)
              .userId(userId);
    }

    public record AuthorBooks(Author author,
                              Map<Book, List<BookCopy>> books) {
    }
}
