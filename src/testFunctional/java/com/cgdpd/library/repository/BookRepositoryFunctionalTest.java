package com.cgdpd.library.repository;

import static com.cgdpd.library.AuthorTestData.AUTHOR_JOHN_DOE;
import static com.cgdpd.library.BookTestData.CHRIS_DANE__ONCE_AGAIN__2022;
import static com.cgdpd.library.BookTestData.JANE_DANE__KILLER__2001;
import static com.cgdpd.library.BookTestData.JOHN_DOE__FINDER__1995;
import static com.cgdpd.library.TestData.booksByAuthorNameCriteria;
import static com.cgdpd.library.TestData.booksByGenreCriteria;
import static com.cgdpd.library.TestData.booksByTitleCriteria;
import static com.cgdpd.library.TestData.booksOlderThan;
import static com.cgdpd.library.TestData.booksYoungerThan;
import static com.cgdpd.library.TestData.johnDoeBooks;
import static com.cgdpd.library.repository.specification.BookSpecifications.byBookSearchCriteria;
import static org.assertj.core.api.Assertions.assertThat;

import com.cgdpd.library.DbPrePopulatedFunctionalTest;
import com.cgdpd.library.dto.book.copy.SearchBookCriteria;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BookRepositoryFunctionalTest extends DbPrePopulatedFunctionalTest {


    @Autowired
    private BookRepository bookRepository;

    @Test
    void should_find_books_by_search_criteria() {
        // given
        var bookTitleCriteria = "i";
        var authorNameCriteria = "j";
        var publicationYearCriteria = (short) 2003;
        var criteria = SearchBookCriteria.builder()
              .authorName(Optional.of(authorNameCriteria))
              .bookTitle(Optional.of(bookTitleCriteria))
              .publicationYearLessThan(Optional.of(publicationYearCriteria))
              .build();

        var spec = byBookSearchCriteria(criteria);

        // when
        var result = bookRepository.findAll(spec);

        // then
        assertThat(result).hasSameElementsAs(
              List.of(
                    JOHN_DOE__FINDER__1995,
                    JANE_DANE__KILLER__2001));
    }

    @Test
    void should_find_books_by_book_title() {
        // given
        var criteria = SearchBookCriteria.builder()
              .bookTitle(Optional.of(CHRIS_DANE__ONCE_AGAIN__2022.getTitle()))
              .build();

        var spec = byBookSearchCriteria(criteria);

        // when
        var result = bookRepository.findAll(spec);

        // then
        assertThat(result).hasSameElementsAs(List.of(CHRIS_DANE__ONCE_AGAIN__2022));
    }

    @Test
    void should_find_books_by_non_specific_book_title() {
        // given
        var bookTitleCriteria = "o";
        var criteria = SearchBookCriteria.builder()
              .bookTitle(Optional.of(bookTitleCriteria))
              .build();

        var spec = byBookSearchCriteria(criteria);

        // when
        var result = bookRepository.findAll(spec);

        // then
        assertThat(result).hasSameElementsAs(booksByTitleCriteria(bookTitleCriteria));
    }

    @Test
    void should_find_books_by_author_name() {
        // given
        var criteria = SearchBookCriteria.builder()
              .authorName(Optional.of(AUTHOR_JOHN_DOE.getName()))
              .build();

        var spec = byBookSearchCriteria(criteria);

        // when
        var result = bookRepository.findAll(spec);

        // then
        assertThat(result).hasSameElementsAs(johnDoeBooks());
    }

    @Test
    void should_find_books_by_non_specific_author_name() {
        // given
        var authorNameCriteria = "j";
        var criteria = SearchBookCriteria.builder()
              .authorName(Optional.of(authorNameCriteria))
              .build();

        var spec = byBookSearchCriteria(criteria);

        // when
        var result = bookRepository.findAll(spec);

        // then
        assertThat(result).hasSameElementsAs(booksByAuthorNameCriteria(authorNameCriteria));
    }

    @Test
    void should_find_books_by_genre() {
        // given
        var genreCriteria = "Suspense";
        var criteria = SearchBookCriteria.builder()
              .genre(Optional.of(genreCriteria))
              .build();

        var spec = byBookSearchCriteria(criteria);

        // when
        var result = bookRepository.findAll(spec);

        // then
        assertThat(result).hasSameElementsAs(booksByGenreCriteria(genreCriteria));
    }

    @Test
    void should_find_books_with_publication_year_older_than_2003() {
        // given
        var publicationYearCriteria = (short) 2003;
        var criteria = SearchBookCriteria.builder()
              .publicationYearGreaterThan(Optional.of(publicationYearCriteria))
              .build();

        var spec = byBookSearchCriteria(criteria);

        // when
        var result = bookRepository.findAll(spec);

        // then
        assertThat(result).hasSameElementsAs(booksOlderThan(publicationYearCriteria));
    }

    @Test
    void should_find_books_with_publication_year_younger_than_2003() {
        // given
        var publicationYearCriteria = (short) 2003;
        var criteria = SearchBookCriteria.builder()
              .publicationYearLessThan(Optional.of(publicationYearCriteria))
              .build();

        var spec = byBookSearchCriteria(criteria);

        // when
        var result = bookRepository.findAll(spec);

        // then
        assertThat(result).hasSameElementsAs(booksYoungerThan(publicationYearCriteria));
    }
}
