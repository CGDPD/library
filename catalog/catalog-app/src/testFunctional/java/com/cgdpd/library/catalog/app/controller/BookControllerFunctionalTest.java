package com.cgdpd.library.catalog.app.controller;

import static com.cgdpd.library.catalog.app.AuthorTestData.anAuthorEntity;
import static com.cgdpd.library.catalog.app.BookCopyEntityTestData.aBookCopyEntity;
import static com.cgdpd.library.catalog.app.BookEntityTestData.aBookEntity;
import static com.cgdpd.library.catalog.app.helper.BookAssertion.assertThatDetailedBookDtoHasCorrectValues;
import static com.cgdpd.library.catalog.domain.BookTestData.aCreateBookRequestDto;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.cgdpd.library.catalog.app.FunctionalTest;
import com.cgdpd.library.catalog.app.entity.AuthorEntity;
import com.cgdpd.library.catalog.app.entity.BookCopyEntity;
import com.cgdpd.library.catalog.app.entity.BookEntity;
import com.cgdpd.library.catalog.app.repository.AuthorRepository;
import com.cgdpd.library.catalog.app.repository.BookCopyRepository;
import com.cgdpd.library.catalog.app.repository.BookRepository;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus;
import com.cgdpd.library.types.Isbn13;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.List;

public class BookControllerFunctionalTest extends FunctionalTest {

    private static final String BASE_ENDPOINT = "/book";


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void should_create_book_and_return_id() {
        // given
        var request = aCreateBookRequestDto().build();

        var authorEntity = anAuthorEntity().build();
        authorRepository.save(authorEntity);

        // when
        var responseEntity = restTemplate.postForEntity(BASE_ENDPOINT, request, Book.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);
        assertThat(responseEntity.hasBody()).isTrue();

        var resultCreatedBook = responseEntity.getBody();
        assertThat(resultCreatedBook.title()).isEqualTo(request.title());
        assertThat(resultCreatedBook.authorId().value()).isEqualTo(authorEntity.getId());
        assertThat(resultCreatedBook.publicationYear()).isEqualTo(request.publicationYear());
        assertThat(resultCreatedBook.isbn()).isEqualTo(request.isbn());
        assertThat(resultCreatedBook.genre()).isEqualTo(request.genre());

        var bookEntity = bookRepository.findById(resultCreatedBook.id().value()).orElseThrow();
        assertThat(bookEntity.getTitle()).isEqualTo(request.title());
        assertThat(bookEntity.getAuthorEntity().getId()).isEqualTo(authorEntity.getId());
        assertThat(bookEntity.getPublicationYear()).isEqualTo(
              request.publicationYear().orElseThrow());
        assertThat(bookEntity.getIsbn()).isEqualTo(request.isbn().value());
        assertThat(bookEntity.getGenre()).isEqualTo(request.genre());
    }

    @Test
    public void should_return_not_found_code_when_author_does_not_exist() throws Exception {
        // given
        var request = aCreateBookRequestDto().build();

        // when
        var responseEntity = restTemplate.postForEntity(BASE_ENDPOINT, request, Book.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    void should_fetch_detailed_book_by_isbn13() {
        // given
        var bookEntity = givenRandomBookExists();
        var bookCopyEntity = givenBookCopyExists(bookEntity);
        var bookCopiyTrackingStatus = TrackingStatus.valueOf(bookCopyEntity.getTrackingStatus());

        // when
        var responseEntity = restTemplate.getForEntity(
              BASE_ENDPOINT + "/" + bookEntity.getIsbn(),
              DetailedBookDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        assertThat(responseEntity.hasBody()).isTrue();

        assertThatDetailedBookDtoHasCorrectValues(
              responseEntity.getBody(),
              bookEntity,
              List.of(bookCopiyTrackingStatus));
    }

    @Test
    void should_return_404_when_isbn_does_not_exist() {
        // when
        var responseEntity = restTemplate.getForEntity(
              BASE_ENDPOINT + "/" + Isbn13.random().value(),
              DetailedBookDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    void should_fetch_detailed_book_without_copies_by_isbn13_with_unavailable_status() {
        // given
        var bookEntity = givenRandomBookExists();

        // when
        var responseEntity = restTemplate.getForEntity(
              BASE_ENDPOINT + "/" + bookEntity.getIsbn(),
              DetailedBookDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        assertThat(responseEntity.hasBody()).isTrue();

        assertThatDetailedBookDtoHasCorrectValues(
              responseEntity.getBody(),
              bookEntity,
              emptyList());
    }

    // TODO: 28/08/2023 LIB-25 Create generic class to handle this
    private BookEntity givenRandomBookExists() {
        var bookEntity = aBookEntity().build();
        return givenBookExists(bookEntity);
    }

    private BookEntity givenBookExists(BookEntity bookEntity) {
        var givenAuthor = givenAuthorExists(anAuthorEntity().id(null).build());

        bookEntity.setAuthorEntity(givenAuthor);
        return bookRepository.save(bookEntity);
    }

    private BookCopyEntity givenBookCopyExists(BookEntity bookEntity) {
        var bookCopyEntity = aBookCopyEntity(bookEntity.getId()).bookEntity(bookEntity).build();
        return bookCopyRepository.save(bookCopyEntity);
    }

    private AuthorEntity givenAuthorExists(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }
}
