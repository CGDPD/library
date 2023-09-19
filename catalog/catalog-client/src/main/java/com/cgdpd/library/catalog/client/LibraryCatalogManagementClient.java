package com.cgdpd.library.catalog.client;

import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDto;
import com.cgdpd.library.catalog.domain.book.model.Book;

import reactor.core.publisher.Mono;

public interface LibraryCatalogManagementClient {

    Mono<Author> createAuthor(CreateAuthorRequestDto createAuthorRequestDto);

    Mono<Book> createBook(CreateBookRequestDto createBookRequestDto);
}
