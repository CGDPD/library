package com.cgdpd.library;

import static com.cgdpd.library.TestData.authors;
import static com.cgdpd.library.TestData.bookCopies;
import static com.cgdpd.library.TestData.books;
import static com.cgdpd.library.TestData.users;

import com.cgdpd.library.entity.AuthorEntity;
import com.cgdpd.library.entity.BookCopyEntity;
import com.cgdpd.library.entity.BookEntity;
import com.cgdpd.library.entity.UserEntity;
import com.cgdpd.library.repository.AuthorRepository;
import com.cgdpd.library.repository.BookCopyRepository;
import com.cgdpd.library.repository.BookRepository;
import com.cgdpd.library.repository.UserRepository;
import java.util.List;
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

        List<UserEntity> users = users();
        LOG.debug("Inserting users {}", users);
        userRepository.saveAll(users);

        List<AuthorEntity> authors = authors();
        LOG.debug("Inserting authors {}", authors);
        authorRepository.saveAll(authors);

        List<BookEntity> books = books();
        LOG.debug("Inserting books {}", books);
        bookRepository.saveAll(books);

        List<BookCopyEntity> bookCopies = bookCopies();
        LOG.debug("Inserting book copies {}", bookCopies);
        bookCopyRepository.saveAll(bookCopies);
    }
}