package com.cgdp.library.service;


import com.cgdp.library.dto.author.AuthorDTO;
import com.cgdp.library.dto.book.BookDTO;
import com.cgdp.library.dto.book.CreateBookRequestDTO;
import com.cgdp.library.entity.AuthorEntity;
import com.cgdp.library.entity.BookEntity;
import com.cgdp.library.repository.AuthorRepository;
import com.cgdp.library.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public BookDTO createBook(CreateBookRequestDTO requestDTO) {

        AuthorEntity authorEntity = authorRepository.findById(requestDTO.getAuthorId())
              .orElseThrow(() -> new IllegalArgumentException("Author with ID " + requestDTO.getAuthorId() + " not found"));
        BookEntity bookEntity = modelMapper.map(requestDTO, BookEntity.class);
        bookEntity.setAuthorEntity(authorEntity);
        BookEntity createdBook = bookRepository.save(bookEntity);
        return modelMapper.map(createdBook, BookDTO.class);
    }
}



