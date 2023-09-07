package com.cgdpd.library.catalog.client;

import static com.cgdpd.library.common.validation.Validator.required;
import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;

import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDTO;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.types.Isbn13;

import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class LibraryCatalogHttpClient implements LibraryCatalogClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public LibraryCatalogHttpClient(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = required("restTemplate", restTemplate);
        this.baseUrl = requiredNotBlank("baseUrl", baseUrl);
    }

    @Override
    public Author createAuthor(CreateAuthorRequestDto createAuthorRequestDto) {
        return restTemplate.postForObject(
              URI.create(baseUrl + "/author"),
              createAuthorRequestDto,
              Author.class);
    }

    @Override
    public Book createBook(CreateBookRequestDto requestDto) {
        return null;
    }

    @Override
    public DetailedBookDTO getBook(Isbn13 isbn13) {
        return null;
    }
}
