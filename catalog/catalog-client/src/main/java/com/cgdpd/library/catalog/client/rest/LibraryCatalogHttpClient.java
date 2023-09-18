package com.cgdpd.library.catalog.client.rest;

import static com.cgdpd.library.common.validation.Validator.required;

import com.cgdpd.library.catalog.client.LibraryCatalogClient;
import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.common.client.InternalHttpClient;
import com.cgdpd.library.types.Isbn13;

public class LibraryCatalogHttpClient extends InternalHttpClient implements LibraryCatalogClient {

    private final LibraryCatalogHttpReactiveClient delegate;

    public LibraryCatalogHttpClient(LibraryCatalogHttpReactiveClient delegate) {
        this.delegate = required("delegate", delegate);
    }

    @Override
    public Author createAuthor(CreateAuthorRequestDto createAuthorRequestDto) {
        return delegate.createAuthor(createAuthorRequestDto).block();
    }

    @Override
    public Book createBook(CreateBookRequestDto createBookRequestDto) {
        return delegate.createBook(createBookRequestDto).block();
    }

    @Override
    public DetailedBookDto getDetailedBookDto(Isbn13 isbn13) {
        return delegate.getDetailedBookDto(isbn13).block();
    }
}
