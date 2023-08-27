package com.cgdpd.library.service;

import com.cgdpd.library.dto.book.BookDTO;
import com.cgdpd.library.dto.book.CreateBookRequestDTO;
import com.cgdpd.library.dto.book.copy.SearchBookCriteria;
import com.cgdpd.library.entity.BookEntity;
import com.cgdpd.library.exceptions.NotFoundException;
import com.cgdpd.library.mapper.BookMapper;
import com.cgdpd.library.repository.BookRepository;
import com.cgdpd.library.repository.specification.BookSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public Page<BookDTO> getBooks(SearchBookCriteria criteria, int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<BookEntity> books = bookRepository.findAll(
              BookSpecifications.byBookSearchCriteria(criteria), pageable);
        return books.map(bookMapper::mapToBookDTO);
    }
}
