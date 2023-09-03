package com.cgdpd.library.catalog.app.service;


import com.cgdpd.library.catalog.app.mapper.BookMapper;
import com.cgdpd.library.catalog.app.repository.BookRepository;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDTO;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDTO;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.common.exception.NotFoundException;
import com.cgdpd.library.types.Isbn13;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;

    public Book createBook(CreateBookRequestDTO requestDTO) {
        if (!authorService.authorExist(requestDTO.authorId())) {
            throw new NotFoundException("Author with id " + requestDTO.authorId() + " not found");
        }
        var bookEntity = bookMapper.mapToBookEntity(requestDTO);
        var createdBook = bookRepository.save(bookEntity);
        return bookMapper.mapToBook(createdBook);
    }

    public DetailedBookDTO getDetailedBookByIsbn13(Isbn13 isbn13) {
        return findDetailedBookByIsbn13(isbn13).orElseThrow(
              () -> new NotFoundException(String.format("No book by the isbn %s", isbn13.value())));
    }

    public Optional<DetailedBookDTO> findDetailedBookByIsbn13(Isbn13 isbn13) {
        return bookRepository.findDetailedBookByIsbn(isbn13.value())
              .map(bookMapper::mapToDetailedBookDto);
    }
}
