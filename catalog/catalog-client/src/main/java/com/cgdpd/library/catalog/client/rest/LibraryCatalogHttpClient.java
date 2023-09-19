package com.cgdpd.library.catalog.client.rest;

import static com.cgdpd.library.common.validation.Validator.required;

import com.cgdpd.library.catalog.client.LibraryCatalogClient;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.common.client.InternalHttpClient;
import com.cgdpd.library.common.type.Isbn13;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class LibraryCatalogHttpClient extends InternalHttpClient
      implements LibraryCatalogClient {

    private final WebClient webClient;

    public LibraryCatalogHttpClient(WebClient webClient) {
        this.webClient = required("webClient", webClient);
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
