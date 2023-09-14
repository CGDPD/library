package com.cgdpd.library.catalog.client;

import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.types.Isbn13;

public interface LibraryCatalogClient {

    Author createAuthor(CreateAuthorRequestDto createAuthorRequestDto);

    Book createBook(CreateBookRequestDto createBookRequestDto);

    DetailedBookDto getDetailedBookDto(Isbn13 isbn13);
}