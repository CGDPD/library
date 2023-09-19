package com.cgdpd.library.catalog.client.rest;

import static com.cgdpd.library.common.validation.Validator.required;

import com.cgdpd.library.catalog.client.LibraryCatalogManagementClient;
import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDto;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.common.client.InternalHttpClient;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class LibraryCatalogHttpManagementClient extends InternalHttpClient
      implements LibraryCatalogManagementClient {

    private final WebClient webClient;

    public LibraryCatalogHttpManagementClient(WebClient webClient) {
        this.webClient = required("webClient", webClient);
    }

    @Override
    public Mono<Author> createAuthor(CreateAuthorRequestDto createAuthorRequestDto) {
        return webClient.post()
              .uri("/management/author")
              .bodyValue(createAuthorRequestDto)
              .retrieve()
              .bodyToMono(Author.class)
              .onErrorMap(onErrorMap());
    }

    @Override
    public Mono<Book> createBook(CreateBookRequestDto createBookRequestDto) {
        return webClient.post()
              .uri("/management/book")
              .bodyValue(createBookRequestDto)
              .retrieve()
              .bodyToMono(Book.class)
              .onErrorMap(onErrorMap());
    }
}
