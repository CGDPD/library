package com.cgdpd.library.catalog.api.rest;

import static com.cgdpd.library.common.validation.Validator.required;
import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;

import com.cgdpd.library.catalog.api.LibraryCatalogClient;
import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.types.Isbn13;

import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class LibraryCatalogHttpClient implements LibraryCatalogClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public LibraryCatalogHttpClient(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = required("restTemplate", restTemplate);
        this.baseUrl = requiredNotBlank("baseUrl", baseUrl); // TODO: 07/09/2023 Validate baseUrl
    }

    @Override
    public Author createAuthor(CreateAuthorRequestDto createAuthorRequestDto) {
        return restTemplate.postForObject(
              URI.create(baseUrl + "/author"),
              createAuthorRequestDto,
              Author.class);
    }

    @Override
    public Book createBook(CreateBookRequestDto createBookRequestDto) {
        return restTemplate.postForObject(
              URI.create(baseUrl + "/book"),
              createBookRequestDto,
              Book.class);
    }

    @Override
    public DetailedBookDto getDetailedBookDto(Isbn13 isbn13) {
        return restTemplate.getForObject(
              URI.create(baseUrl + "/book/isbn13/" + isbn13.value()),
              DetailedBookDto.class);
    }
}
