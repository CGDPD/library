package com.cgdpd.library.catalog.api;


import static com.cgdpd.library.catalog.api.TestData.authors;
import static com.cgdpd.library.catalog.api.TestData.bookCopies;
import static com.cgdpd.library.catalog.api.TestData.books;

import com.cgdpd.library.catalog.api.repository.AuthorRepository;
import com.cgdpd.library.catalog.api.repository.BookCopyRepository;
import com.cgdpd.library.catalog.api.repository.BookRepository;

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
