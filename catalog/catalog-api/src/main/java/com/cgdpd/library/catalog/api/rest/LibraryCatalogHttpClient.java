package com.cgdpd.library.catalog.api.rest;

import static com.cgdpd.library.common.validation.Validator.required;

import com.cgdpd.library.catalog.api.LibraryCatalogClient;
import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.types.Isbn13;

import org.springframework.web.client.RestTemplate;

public class LibraryCatalogHttpClient implements LibraryCatalogClient {

    private final RestTemplate restTemplate;

    public LibraryCatalogHttpClient(RestTemplate restTemplate) {
        this.restTemplate = required("restTemplate", restTemplate);
    }

    @Override
    public Author createAuthor(CreateAuthorRequestDto createAuthorRequestDto) {
        return restTemplate.postForObject("/author", createAuthorRequestDto, Author.class);
    }

    @Override
    public Book createBook(CreateBookRequestDto createBookRequestDto) {
        return restTemplate.postForObject("/book", createBookRequestDto, Book.class);
    }

    @Override
    public DetailedBookDto getDetailedBookDto(Isbn13 isbn13) {
        return restTemplate.getForObject("/book/isbn13/" + isbn13.value(), DetailedBookDto.class);
    }
}
