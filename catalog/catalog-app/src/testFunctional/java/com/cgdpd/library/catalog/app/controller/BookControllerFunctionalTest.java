package com.cgdpd.library.catalog.app.controller;

import static com.cgdpd.library.catalog.app.AuthorTestData.anAuthorEntity;
import static com.cgdpd.library.catalog.app.BookCopyEntityTestData.aBookCopyEntity;
import static com.cgdpd.library.catalog.app.BookEntityTestData.aBookEntity;
import static com.cgdpd.library.catalog.app.helper.BookAssertion.assertThatDetailedBookDtoHasCorrectValues;
import static com.cgdpd.library.catalog.app.helper.TestUtils.getJsonObjectFromResult;
import static com.cgdpd.library.catalog.app.helper.TestUtils.getObjectFromResultActions;
import static com.cgdpd.library.catalog.domain.BookTestData.aCreateBookRequestDTO;
import static com.cgdpd.library.catalog.domain.book.dto.BookAvailability.UNAVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cgdpd.library.catalog.app.FunctionalTest;
import com.cgdpd.library.catalog.app.entity.AuthorEntity;
import com.cgdpd.library.catalog.app.entity.BookCopyEntity;
import com.cgdpd.library.catalog.app.entity.BookEntity;
import com.cgdpd.library.catalog.app.repository.AuthorRepository;
import com.cgdpd.library.catalog.app.repository.BookCopyRepository;
import com.cgdpd.library.catalog.app.repository.BookRepository;
import com.cgdpd.library.catalog.domain.book.dto.BookAvailability;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDTO;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus;
import com.cgdpd.library.types.Isbn13;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;

@AutoConfigureMockMvc
public class BookControllerFunctionalTest extends FunctionalTest {

    private static final String BASE_ENDPOINT = "/book";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_create_book_and_return_id() throws Exception {
        // given
        var request = aCreateBookRequestDTO().build();

        var authorEntity = anAuthorEntity().build();
        authorRepository.save(authorEntity);

        // when
        var resultActions = mockMvc.perform(post(BASE_ENDPOINT)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions
              .andExpect(status().isCreated());

        var resultCreatedBook = getObjectFromResultActions(resultActions, Book.class, objectMapper);
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
        var request = aCreateBookRequestDTO().build();

        // when
        var resultActions = mockMvc.perform(post(BASE_ENDPOINT)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void should_fetch_detailed_book_by_isbn13() throws Exception {
        // given
        var bookEntity = givenRandomBookExists();
        var bookCopyEntity = givenBookCopyExists(bookEntity);
        var bookCopiyTrackingStatus = TrackingStatus.valueOf(bookCopyEntity.getTrackingStatus());

        // when
        var resultActions = mockMvc.perform(get(BASE_ENDPOINT + "/isbn13/" + bookEntity.getIsbn())
              .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
        var resultDetailedBookDto = getObjectFromResultActions(resultActions, DetailedBookDTO.class,
              objectMapper);
        assertThatDetailedBookDtoHasCorrectValues(
              resultDetailedBookDto,
              bookEntity,
              BookAvailability.fromTrackingStatus(bookCopiyTrackingStatus));
    }

    @Test
    void should_return_404_when_isbn_does_not_exist() throws Exception {
        // when
        var resultActions = mockMvc.perform(
              get(BASE_ENDPOINT + "/isbn13/" + Isbn13.random().value())
                    .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void should_fetch_detailed_book_without_copies_by_isbn13_with_unavailable_status()
          throws Exception {
        // given
        var bookEntity = givenRandomBookExists();

        // when
        var resultActions = mockMvc.perform(get(BASE_ENDPOINT + "/isbn13/" + bookEntity.getIsbn())
              .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
        var resultDetailedBookDto = getObjectFromResultActions(resultActions, DetailedBookDTO.class,
              objectMapper);
        assertThatDetailedBookDtoHasCorrectValues(resultDetailedBookDto, bookEntity, UNAVAILABLE);
    }

    private Long getIdFromResult(ResultActions resultActions)
          throws UnsupportedEncodingException, JSONException {
        var jsonObject = getJsonObjectFromResult(resultActions);
        return jsonObject.getLong("bookId");
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
