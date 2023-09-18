package com.cgdpd.library.catalog.client.stub;

import static com.cgdpd.library.catalog.domain.BookCopyTestData.aBookCopy;
import static com.cgdpd.library.catalog.domain.BookTestData.aCreateBookRequestDto;
import static com.cgdpd.library.catalog.domain.BookTestData.bookFromRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cgdpd.library.catalog.domain.author.AuthorId;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;
import com.cgdpd.library.common.exception.NotFoundException;

import org.junit.jupiter.api.Test;

class LibraryCatalogStubClientTest {


    private final LibraryCatalogStubClient libraryCatalogStubClient = new LibraryCatalogStubClient();

    @Test
    void should_create_author() {
        // given
        var createAuthorRequestDto = new CreateAuthorRequestDto("John Doe");

        // when
        var resultCreatedAuthor = libraryCatalogStubClient.createAuthor(createAuthorRequestDto)
              .block();

        // then
        var createdAuthor = libraryCatalogStubClient.authors().get(resultCreatedAuthor.id());
        assertThat(resultCreatedAuthor.name()).isEqualTo(createAuthorRequestDto.authorName());
        assertThat(resultCreatedAuthor).isEqualTo(createdAuthor);
    }

    @Test
    void should_create_book() {
        // given
        var createAuthorRequestDto = new CreateAuthorRequestDto("John Doe");
        var givenAuthor = libraryCatalogStubClient.createAuthor(createAuthorRequestDto).block();
        var createBookRequestDto = aCreateBookRequestDto().authorId(givenAuthor.id()).build();

        // when
        var resultCreatedBook = libraryCatalogStubClient.createBook(createBookRequestDto).block();

        // then
        var createdBook = libraryCatalogStubClient.books().get(resultCreatedBook.id());
        var expectedBook = bookFromRequest(createBookRequestDto).id(createdBook.id()).build();
        assertThat(resultCreatedBook).isEqualTo(expectedBook);
    }

    @Test
    void should_throw_exception_when_creating_book_with_not_existing_author() {
        // given
        var createBookRequestDto = aCreateBookRequestDto().authorId(AuthorId.of(999L)).build();

        // when then
        assertThatThrownBy(() -> libraryCatalogStubClient.createBook(createBookRequestDto).block())
              .isInstanceOf(NotFoundException.class);
    }

    @Test
    void should_get_detailed_book_by_isbn13() {
        // given
        var createAuthorRequestDto = new CreateAuthorRequestDto("John Doe");
        var givenAuthor = libraryCatalogStubClient.createAuthor(createAuthorRequestDto).block();
        var createBookRequestDto = aCreateBookRequestDto().authorId(givenAuthor.id()).build();
        var givenBook = libraryCatalogStubClient.createBook(createBookRequestDto).block();
        var givenBookCopy = aBookCopy().bookId(givenBook.id()).build();
        libraryCatalogStubClient.addBookCopy(givenBookCopy);
        var isbn13 = givenBook.isbn();

        // when
        var resultDetailedBookDto = libraryCatalogStubClient.getDetailedBookDto(isbn13).block();

        // then
        assertThat(resultDetailedBookDto.id()).isEqualTo(givenBook.id());
        assertThat(resultDetailedBookDto.title()).isEqualTo(givenBook.title());
        assertThat(resultDetailedBookDto.authorId()).isEqualTo(givenAuthor.id());
        assertThat(resultDetailedBookDto.authorName()).isEqualTo(givenAuthor.name());
        assertThat(resultDetailedBookDto.isbn()).isEqualTo(givenBook.isbn());
        assertThat(resultDetailedBookDto.genre()).isEqualTo(givenBook.genre());
        assertThat(resultDetailedBookDto.trackingStatusList())
              .containsExactlyInAnyOrder(givenBookCopy.trackingStatus());
        assertThat(resultDetailedBookDto.publicationYear()).isEqualTo(givenBook.publicationYear());
    }
}
