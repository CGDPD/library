package com.cgdpd.library.catalog.app;

import static com.cgdpd.library.catalog.app.TestData.authors;
import static com.cgdpd.library.catalog.app.TestData.bookCopies;
import static com.cgdpd.library.catalog.app.TestData.books;

import com.cgdpd.library.catalog.app.repository.AuthorRepository;
import com.cgdpd.library.catalog.app.repository.BookCopyRepository;
import com.cgdpd.library.catalog.app.repository.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DbPrePopulatedFunctionalTest extends FunctionalTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;


    @Override
    @BeforeEach
    void setUp() {
        super.setUp();

        var authors = authors();
        authorRepository.saveAll(authors);

        var books = books();
        bookRepository.saveAll(books);

        var bookCopies = bookCopies();
        bookCopyRepository.saveAll(bookCopies);
    }
}
