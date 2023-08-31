package com.cgdpd.library.controller;

import static com.cgdpd.library.AuthorTestData.anAuthorEntity;
import static com.cgdpd.library.BookCopyTestData.aBookCopyEntity;
import static com.cgdpd.library.BookTestData.aBookEntity;
import static com.cgdpd.library.BookTestData.aCreateBookRequestDTO;
import static com.cgdpd.library.RandomIsbn.generateISBN13;
import static com.cgdpd.library.TestUtils.getJsonObjectFromResult;
import static com.cgdpd.library.TestUtils.getObjectFromResultActions;
import static com.cgdpd.library.dto.book.BookAvailability.UNAVAILABLE;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cgdpd.library.FunctionalTest;
import com.cgdpd.library.dto.book.BookAvailability;
import com.cgdpd.library.dto.book.DetailedBookDTO;
import com.cgdpd.library.entity.AuthorEntity;
import com.cgdpd.library.entity.BookCopyEntity;
import com.cgdpd.library.entity.BookEntity;
import com.cgdpd.library.model.book.copy.TrackingStatus;
import com.cgdpd.library.repository.AuthorRepository;
import com.cgdpd.library.repository.BookCopyRepository;
import com.cgdpd.library.repository.BookRepository;
import com.cgdpd.library.type.AuthorId;
import com.cgdpd.library.type.BookId;
import com.cgdpd.library.type.Isbn13;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@AutoConfigureMockMvc
public class BookControllerFunctionalTest extends FunctionalTest {
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
        var resultActions = mockMvc.perform(post("/books")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.bookId", is(Long.class)).exists());

        var id = getIdFromResult(resultActions);
        var bookEntity = bookRepository.findById(id).orElseThrow();
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
        var resultActions = mockMvc.perform(post("/books")
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
        var resultActions = mockMvc.perform(get("/books/isbn13/" + bookEntity.getIsbn())
              .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
        var resultBook = getObjectFromResultActions(resultActions, DetailedBookDTO.class,
              objectMapper);
        assertThatDetailedBookHasCorrectValues(
              resultBook,
              bookEntity,
              BookAvailability.fromTrackingStatus(bookCopiyTrackingStatus));
    }

    @Test
    void should_return_404_when_isbn_does_not_exist() throws Exception {
        // when
        var resultActions = mockMvc.perform(get("/books/isbn13/" + generateISBN13().value())
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
        var resultActions = mockMvc.perform(get("/books/isbn13/" + bookEntity.getIsbn())
              .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
        var resultBook = getObjectFromResultActions(resultActions, DetailedBookDTO.class,
              objectMapper);
        assertThatDetailedBookHasCorrectValues(resultBook, bookEntity, UNAVAILABLE);
    }

    private void assertThatDetailedBookHasCorrectValues(DetailedBookDTO resultBook,
                                                        BookEntity bookEntity,
                                                        BookAvailability bookAvailability) {
        assertThat(resultBook.id()).isEqualTo(BookId.of(bookEntity.getId()));
        assertThat(resultBook.title()).isEqualTo(bookEntity.getTitle());
        assertThat(resultBook.authorId()).isEqualTo(
              AuthorId.of(bookEntity.getAuthorEntity().getId()));
        assertThat(resultBook.authorName()).isEqualTo(bookEntity.getAuthorEntity().getName());
        assertThat(resultBook.isbn()).isEqualTo(Isbn13.of(bookEntity.getIsbn()));
        assertThat(resultBook.genre()).isEqualTo(bookEntity.getGenre());
        assertThat(resultBook.availability()).isEqualTo(bookAvailability);
        assertThat(resultBook.publicationYear()).isEqualTo(
              Optional.of(bookEntity.getPublicationYear()));
    }

    private Long getIdFromResult(ResultActions resultActions)
          throws UnsupportedEncodingException, JSONException {
        var jsonObject = getJsonObjectFromResult(resultActions);
        return jsonObject.getLong("bookId");
    }

    // TODO: 28/08/2023 LIB-25 Create generic class to handle this
    private BookEntity givenRandomBookExists() {
        var bookEntity = aBookEntity().id(null).build();
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
