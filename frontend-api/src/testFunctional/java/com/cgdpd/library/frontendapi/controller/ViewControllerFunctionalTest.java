package com.cgdpd.library.frontendapi.controller;

import static com.cgdpd.library.frontendapi.helper.BookAssertion.assertThatDetailedBookDtoHasCorrectValues;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.cgdpd.library.catalog.client.stub.CatalogInMemoryDb;
import com.cgdpd.library.catalog.client.stub.LibraryCatalogStubClient;
import com.cgdpd.library.catalog.domain.book.model.copy.BookCopy;
import com.cgdpd.library.common.error.ErrorResponse;
import com.cgdpd.library.frontendapi.FunctionalTest;
import com.cgdpd.library.frontendapi.dto.BookAvailability;
import com.cgdpd.library.frontendapi.dto.BookViewDto;
import com.cgdpd.library.common.type.Isbn13;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;

public class ViewControllerFunctionalTest extends FunctionalTest {

    private static final String BASE_ENDPOINT = "/view";

    @Autowired
    private CatalogInMemoryDb catalogInMemoryDb;

    @Autowired
    private LibraryCatalogStubClient libraryCatalogStubClient;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void should_return_book_view_by_isbn_when_isbn_exists() {
        // given
        var authorBooks = catalogInMemoryDb.generateAuthorAndBooks(5, 5);
        var aBook = authorBooks.books().keySet().stream().findFirst().orElseThrow();
        var trackingStatusList = authorBooks.books().get(aBook).stream()
              .map(BookCopy::trackingStatus)
              .toList();

        // when
        var resultEntity = restTemplate.getForEntity(
              URI.create(BASE_ENDPOINT + "/book/" + aBook.isbn().value()), BookViewDto.class);

        // then
        assertThat(resultEntity.getStatusCode()).isEqualTo(OK);
        assertThat(resultEntity.hasBody()).isTrue();
        assertThatDetailedBookDtoHasCorrectValues(
              resultEntity.getBody(),
              aBook,
              authorBooks.author(),
              BookAvailability.fromTrackingStatusList(trackingStatusList));
    }

    @Test
    void should_return_404_status_when_book_by_isbn_doesnt_exist() {
        // when
        var resultEntity = restTemplate.getForEntity(
              URI.create(BASE_ENDPOINT + "/book/" + Isbn13.random().value()), ErrorResponse.class);

        // then
        assertThat(resultEntity.getStatusCode()).isEqualTo(NOT_FOUND);
    }
}
