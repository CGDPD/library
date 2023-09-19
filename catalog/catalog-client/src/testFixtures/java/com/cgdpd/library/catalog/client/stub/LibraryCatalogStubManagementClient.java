package com.cgdpd.library.catalog.client.stub;

import static com.cgdpd.library.common.validation.Validator.required;

import com.cgdpd.library.catalog.client.LibraryCatalogManagementClient;
import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDto;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.common.exception.NotFoundException;

import reactor.core.publisher.Mono;

public class LibraryCatalogStubManagementClient implements LibraryCatalogManagementClient {

    private final CatalogInMemoryDb inMemoryDb;

    public LibraryCatalogStubManagementClient(CatalogInMemoryDb inMemoryDb) {
        this.inMemoryDb = required("inMemoryDb", inMemoryDb);
    }

    @Override
    public Mono<Author> createAuthor(CreateAuthorRequestDto createAuthorRequestDto) {
        return Mono.fromSupplier(() -> inMemoryDb.addAuthor(
              Author.builder().name(createAuthorRequestDto.authorName())));
    }

    @Override
    public Mono<Book> createBook(CreateBookRequestDto createBookRequestDto) {
        return Mono.fromSupplier(() -> {
            if (!inMemoryDb.authors().containsKey(createBookRequestDto.authorId())) {
                throw new NotFoundException(
                      "Author with id " + createBookRequestDto.authorId() + " not found");
            }
            return inMemoryDb.addBook(Book.builder()
                  .title(createBookRequestDto.title())
                  .authorId(createBookRequestDto.authorId())
                  .isbn(createBookRequestDto.isbn())
                  .genre(createBookRequestDto.genre())
                  .publicationYear(createBookRequestDto.publicationYear()));
        });
    }
}
