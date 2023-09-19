package com.cgdpd.library.catalog.client.stub;

import static com.cgdpd.library.catalog.domain.BookCopyTestData.aBookCopy;
import static com.cgdpd.library.catalog.domain.BookTestData.aBook;
import static org.assertj.core.api.Assertions.assertThat;

import com.cgdpd.library.catalog.domain.author.Author;

import org.junit.jupiter.api.Test;

class LibraryCatalogStubClientTest {

    private final CatalogInMemoryDb catalogInMemoryDb = new CatalogInMemoryDb();
    private final LibraryCatalogStubClient libraryCatalogStubClient
          = new LibraryCatalogStubClient(catalogInMemoryDb);

    @Test
    void should_get_detailed_book_by_isbn13() {
        // given
        var givenAuthor = catalogInMemoryDb.addAuthor(Author.builder().name("John Doe"));
        var givenBook = catalogInMemoryDb.addBook(aBook());
        var givenBookCopy = catalogInMemoryDb.addBookCopy(aBookCopy().bookId(givenBook.id()));
        var isbn13 = givenBook.isbn();

        // when
        var resultDetailedBookDto = libraryCatalogStubClient.getDetailedBookDto(isbn13)
              .blockOptional()
              .orElseThrow();

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
