package com.cgdpd.library.frontendapi.controller;

import static com.cgdpd.library.frontendapi.helper.BookAssertion.assertThatDetailedBookDtoHasCorrectValues;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.cgdpd.library.catalog.client.stub.LibraryCatalogStubClient;
import com.cgdpd.library.catalog.domain.book.model.copy.BookCopy;
import com.cgdpd.library.frontendapi.FunctionalTest;
import com.cgdpd.library.frontendapi.dto.BookAvailability;
import com.cgdpd.library.frontendapi.dto.BookViewDto;
import com.cgdpd.library.frontendapi.error.FrontendError;
import com.cgdpd.library.types.Isbn13;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.URI;

@AutoConfigureMockMvc
public class ViewControllerFunctionalTest extends FunctionalTest {

    private static final String BASE_ENDPOINT = "/view";

    @Autowired
    private LibraryCatalogStubClient libraryCatalogStubClient;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void should_return_book_view_by_isbn_when_isbn_exists() {
        // given
        var authorBooks = libraryCatalogStubClient.generateAuthorAndBooks(5, 5);
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
        // given
        // when
        var resultEntity = restTemplate.getForEntity(
              URI.create(BASE_ENDPOINT + "/book/" + Isbn13.random().value()), FrontendError.class);

        // then
        assertThat(resultEntity.getStatusCode()).isEqualTo(NOT_FOUND);
    }
}
