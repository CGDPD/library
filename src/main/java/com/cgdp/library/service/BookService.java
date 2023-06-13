package com.cgdp.library.service;

import com.cgdp.library.dto.book.BookDTO;
import com.cgdp.library.dto.book.CreateBookRequestDTO;
import com.cgdp.library.entity.BookEntity;
import com.cgdp.library.exceptions.NotFoundException;
import com.cgdp.library.mapper.BookMapper;
import com.cgdp.library.repository.BookRepository;
import lombok.AllArgsConstructor;
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
        BookEntity bookEntity = bookMapper.mapToBookEntity(requestDTO);
        BookEntity createdBook = bookRepository.save(bookEntity);
        return bookMapper.mapToBookDTO(createdBook);
    }
}
