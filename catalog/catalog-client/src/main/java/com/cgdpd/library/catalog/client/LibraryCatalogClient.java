package com.cgdpd.library.catalog.client;

import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.common.type.Isbn13;

import reactor.core.publisher.Mono;

public interface LibraryCatalogClient {

    Mono<DetailedBookDto> getDetailedBookDto(Isbn13 isbn13);
}
