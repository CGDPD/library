package com.cgdpd.library.catalog.client.stub;

import static com.cgdpd.library.catalog.domain.BookTestData.aCreateBookRequestDto;
import static com.cgdpd.library.catalog.domain.BookTestData.bookFromRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.AuthorId;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;
import com.cgdpd.library.common.exception.NotFoundException;

import org.junit.jupiter.api.Test;

public class LibraryCatalogStubManagementClientTest {

    private final CatalogInMemoryDb catalogInMemoryDb = new CatalogInMemoryDb();

    private final LibraryCatalogStubManagementClient client
          = new LibraryCatalogStubManagementClient(catalogInMemoryDb);

    @Test
    void should_create_author() {
        // given
        var createAuthorRequestDto = new CreateAuthorRequestDto("John Doe");

        // when
        var resultCreatedAuthor = client.createAuthor(createAuthorRequestDto)
              .blockOptional()
              .orElseThrow();

        // then
        var createdAuthor = catalogInMemoryDb.authors().get(resultCreatedAuthor.id());
        assertThat(resultCreatedAuthor.name()).isEqualTo(createAuthorRequestDto.authorName());
        assertThat(resultCreatedAuthor).isEqualTo(createdAuthor);
    }

    @Test
    void should_create_book() {
        // given
        var givenAuthor = catalogInMemoryDb.addAuthor(Author.builder().name("John Doe"));
        var createBookRequestDto = aCreateBookRequestDto().authorId(givenAuthor.id()).build();

        // when
        var resultCreatedBook = client.createBook(createBookRequestDto)
              .blockOptional()
              .orElseThrow();

        // then
        var createdBook = catalogInMemoryDb.books().get(resultCreatedBook.id());
        var expectedBook = bookFromRequest(createBookRequestDto).id(createdBook.id()).build();
        assertThat(resultCreatedBook).isEqualTo(expectedBook);
    }

    @Test
    void should_throw_exception_when_creating_book_with_not_existing_author() {
        // given
        var createBookRequestDto = aCreateBookRequestDto().authorId(AuthorId.of(999L)).build();

        // when then
        assertThatThrownBy(
              () -> client.createBook(createBookRequestDto).block())
              .isInstanceOf(NotFoundException.class);
    }
}
