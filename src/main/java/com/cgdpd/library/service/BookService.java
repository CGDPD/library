package com.cgdpd.library.service;

import com.cgdpd.library.dto.book.CreateBookRequestDTO;
import com.cgdpd.library.dto.book.SearchBookCriteria;
import com.cgdpd.library.dto.pagination.PagedResponse;
import com.cgdpd.library.entity.BookEntity;
import com.cgdpd.library.exceptions.NotFoundException;
import com.cgdpd.library.mapper.BookMapper;
import com.cgdpd.library.model.book.Book;
import com.cgdpd.library.repository.BookRepository;
import com.cgdpd.library.repository.specification.BookSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public PagedResponse<Book> getBooks(SearchBookCriteria criteria, int pageIndex, int pageSize,
                                        Sort sort) {
        var pageable = PageRequest.of(pageIndex, pageSize, sort);
        Page<BookEntity> books = bookRepository.findAll(
              BookSpecifications.byBookSearchCriteria(criteria), pageable);
        PagedResponse<Book> response = new PagedResponse<>(
              books.map(bookMapper::mapToBook).getContent(), books.getNumber(), books.getSize(),
              books.getTotalElements());
        return response;
    }

}
