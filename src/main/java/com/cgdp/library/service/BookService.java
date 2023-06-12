package com.cgdp.library.service;

import com.cgdp.library.dto.book.BookDTO;
import com.cgdp.library.dto.book.CreateBookRequestDTO;
import com.cgdp.library.entity.AuthorEntity;
import com.cgdp.library.entity.BookEntity;
import com.cgdp.library.exceptions.NotFoundException;
import com.cgdp.library.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final AuthorService authorService;

    public BookDTO createBook(CreateBookRequestDTO requestDTO) {
        AuthorEntity authorEntity = authorService.getAuthorById(requestDTO.authorId());
        if (authorEntity == null) {
            throw new NotFoundException("Author not found");
        }
        BookEntity bookEntity = modelMapper.map(requestDTO, BookEntity.class);
        bookEntity.setAuthorEntity(authorEntity);
        BookEntity createdBook = bookRepository.save(bookEntity);
        return modelMapper.map(createdBook, BookDTO.class);
    }
}
