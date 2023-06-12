package com.cgdp.library.service;

import com.cgdp.library.dto.book.BookDTO;
import com.cgdp.library.dto.book.CreateBookRequestDTO;
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
        boolean authorExist = authorService.doesAuthorExist(requestDTO.authorId());
        if (!authorExist) {
            throw new NotFoundException("Author not found");
        }
        BookEntity bookEntity = modelMapper.map(requestDTO, BookEntity.class);
        BookEntity createdBook = bookRepository.save(bookEntity);
        return modelMapper.map(createdBook, BookDTO.class);
    }
}
