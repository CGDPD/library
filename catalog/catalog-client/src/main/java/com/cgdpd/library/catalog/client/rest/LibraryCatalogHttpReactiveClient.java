package com.cgdpd.library.catalog.client.rest;

import static com.cgdpd.library.common.validation.Validator.required;

import com.cgdpd.library.catalog.client.LibraryCatalogReactiveClient;
import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.common.client.InternalHttpClient;
import com.cgdpd.library.types.Isbn13;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class LibraryCatalogHttpReactiveClient extends InternalHttpClient
      implements LibraryCatalogReactiveClient {

    private final WebClient webClient;

    public LibraryCatalogHttpReactiveClient(WebClient webClient) {
        this.webClient = required("webClient", webClient);
    }

    @Override
    public Mono<Author> createAuthor(CreateAuthorRequestDto createAuthorRequestDto) {
        return webClient.post()
              .uri("/author")
              .bodyValue(createAuthorRequestDto)
              .retrieve()
              .bodyToMono(Author.class)
              .onErrorMap(onErrorMap());
    }

    @Override
    public Mono<Book> createBook(CreateBookRequestDto createBookRequestDto) {
        return webClient.post()
              .uri("/book")
              .bodyValue(createBookRequestDto)
              .retrieve()
              .bodyToMono(Book.class)
              .onErrorMap(onErrorMap());
    }

    @Override
    public Mono<DetailedBookDto> getDetailedBookDto(Isbn13 isbn13) {
        return webClient.get()
              .uri("/book/" + isbn13.value())
              .retrieve()
              .bodyToMono(DetailedBookDto.class)
              .onErrorMap(onErrorMap());
    }
}
