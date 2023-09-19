package com.cgdpd.library.catalog.app.controller;

import static com.cgdpd.library.catalog.app.AuthorTestData.anAuthorEntity;
import static com.cgdpd.library.catalog.app.BookCopyEntityTestData.aBookCopyEntity;
import static com.cgdpd.library.catalog.app.BookEntityTestData.aBookEntity;
import static com.cgdpd.library.catalog.app.helper.BookAssertion.assertThatDetailedBookDtoHasCorrectValues;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.cgdpd.library.catalog.app.FunctionalTest;
import com.cgdpd.library.catalog.app.entity.AuthorEntity;
import com.cgdpd.library.catalog.app.entity.BookCopyEntity;
import com.cgdpd.library.catalog.app.entity.BookEntity;
import com.cgdpd.library.catalog.app.mapper.BookMapper;
import com.cgdpd.library.catalog.app.repository.AuthorRepository;
import com.cgdpd.library.catalog.app.repository.BookCopyRepository;
import com.cgdpd.library.catalog.app.repository.BookRepository;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.dto.SearchBookCriteria;
import com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus;
import com.cgdpd.library.common.error.ErrorResponse;
import com.cgdpd.library.common.pagination.PagedResponse;
import com.cgdpd.library.common.pagination.PaginationCriteria;
import com.cgdpd.library.common.type.Isbn13;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class BookControllerFunctionalTest extends FunctionalTest {

    private static final String BASE_ENDPOINT = "/book";

    private TestRestTemplate restTemplate;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setBasicAuth(@Autowired TestRestTemplate testRestTemplate) {
        restTemplate = testRestTemplate.withBasicAuth("FrontendApi", "FrontendApiSecret");
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
              ErrorResponse.class);

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

    @Test
    void should_return_book_with_criteria() throws Exception {
        // given
        var searchCriteria = SearchBookCriteria.builder().build();
        var paginationCriteria = PaginationCriteria.builder()
              .pageIndex(0)
              .pageSize(10)
              .build();

        var bookEntity = givenRandomBookExists();
        var expectedResponse = PagedResponse.<DetailedBookDto>builder()
              .content(List.of(bookMapper.mapToDetailedBookDto(bookEntity)))
              .pageNumber(0)
              .pageSize(10)
              .totalElements(1)
              .totalPages(1)
              .build();
        var uri = UriComponentsBuilder.fromHttpUrl(restTemplate.getRootUri() + BASE_ENDPOINT)
              .queryParam("searchCriteria", objectMapper.writeValueAsString(searchCriteria))
              .queryParam("paginationCriteria", objectMapper.writeValueAsString(paginationCriteria))
              .build()
              .toUri();
        var responseType = new ParameterizedTypeReference<PagedResponse<DetailedBookDto>>() {};

        // when
        var responseEntity = restTemplate.exchange(uri, GET, null, responseType);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        assertThat(responseEntity.hasBody()).isTrue();
        assertThat(responseEntity.getBody()).isEqualTo(expectedResponse);
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
