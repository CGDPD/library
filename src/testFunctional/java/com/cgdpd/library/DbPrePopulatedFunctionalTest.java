package com.cgdpd.library;

import static com.cgdpd.library.TestData.authors;
import static com.cgdpd.library.TestData.bookCopies;
import static com.cgdpd.library.TestData.books;
import static com.cgdpd.library.TestData.users;

import com.cgdpd.library.repository.AuthorRepository;
import com.cgdpd.library.repository.BookCopyRepository;
import com.cgdpd.library.repository.BookRepository;
import com.cgdpd.library.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DbPrePopulatedFunctionalTest extends FunctionalTest {

    private static final Logger LOG = LoggerFactory.getLogger(DbPrePopulatedFunctionalTest.class);

    @Autowired
    private UserRepository userRepository;

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

        var users = users();
        userRepository.saveAll(users);

        var authors = authors();
        authorRepository.saveAll(authors);

        var books = books();
        bookRepository.saveAll(books);

        var bookCopies = bookCopies();
        bookCopyRepository.saveAll(bookCopies);
    }
}
