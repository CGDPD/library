package com.cgdpd.library.catalog.app.controller;

import static com.cgdpd.library.catalog.app.AuthorTestData.anAuthorEntity;
import static com.cgdpd.library.catalog.domain.BookTestData.aCreateBookRequestDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.cgdpd.library.catalog.app.FunctionalTest;
import com.cgdpd.library.catalog.app.repository.AuthorRepository;
import com.cgdpd.library.catalog.app.repository.BookRepository;
import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.common.error.ErrorResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;
import java.util.Map;

public class ManagementControllerFunctionalTest extends FunctionalTest {

    private static final String BASE_ENDPOINT = "/management";

    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;


    @BeforeEach
    void setBasicAuth(@Autowired TestRestTemplate testRestTemplate) {
        restTemplate = testRestTemplate.withBasicAuth("Backoffice", "BackofficeSecret");
    }

    @Test
    public void should_create_author_and_return_name() {
        // given
        var authorName = "John Doe";
        var createAuthorRequestDto = new CreateAuthorRequestDto(authorName);

        // when
        var responseEntity = restTemplate
              .postForEntity(BASE_ENDPOINT + "/author", createAuthorRequestDto, Author.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);
        assertThat(responseEntity.hasBody()).isTrue();

        var responseBody = responseEntity.getBody();
        assertThat(responseBody.name()).isEqualTo(authorName);

        var authorEntity = authorRepository.findById(responseBody.id().value());
        assertThat(authorEntity).isPresent();
        assertThat(authorEntity.get().getName()).isEqualTo(authorName);
    }

    @Test
    public void should_return_bad_request_when_author_name_is_empty() {
        // given
        var requestBody = """
              {
                  "authorName": ""
              }
              """;

        var res = new LinkedMultiValueMap<>(Map.of(CONTENT_TYPE, List.of("application/json")));

        var request = new HttpEntity<>(requestBody, res);
        // when
        var responseEntity = restTemplate.postForEntity(
              BASE_ENDPOINT + "/author",
              request,
              ErrorResponse.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void should_create_book_and_return_id() {
        // given
        var request = aCreateBookRequestDto().build();

        var authorEntity = anAuthorEntity().build();
        authorRepository.save(authorEntity);

        // when
        var responseEntity = restTemplate
              .postForEntity(BASE_ENDPOINT + "/book", request, Book.class);

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
    public void should_return_not_found_code_when_author_does_not_exist() {
        // given
        var request = aCreateBookRequestDto().build();

        // when
        var responseEntity = restTemplate.postForEntity(
              BASE_ENDPOINT + "/book",
              request,
              ErrorResponse.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(NOT_FOUND);
    }
}
