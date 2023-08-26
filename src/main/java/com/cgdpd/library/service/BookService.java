package com.cgdpd.library.service;

import com.cgdpd.library.dto.book.BookDTO;
import com.cgdpd.library.dto.book.CreateBookRequestDTO;
import com.cgdpd.library.entity.BookEntity;
import com.cgdpd.library.exceptions.NotFoundException;
import com.cgdpd.library.mapper.BookMapper;
import com.cgdpd.library.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;

    public BookDTO createBook(CreateBookRequestDTO requestDTO) {
        if (!authorService.authorExist(requestDTO.authorId())) {
            throw new NotFoundException("Author with id " + requestDTO.authorId() + " not found");
        }
        var bookEntity = bookMapper.mapToBookEntity(requestDTO);
        var createdBook = bookRepository.save(bookEntity);
        return bookMapper.mapToBookDTO(createdBook);
    }

    public Page<BookDTO> getBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookEntity> books = bookRepository.findAll(pageable);
        return books.map(bookMapper::mapToBookDTO);
    }
}
