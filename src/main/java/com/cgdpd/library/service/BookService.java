package com.cgdpd.library.service;

import com.cgdpd.library.dto.book.CreateBookRequestDTO;
import com.cgdpd.library.dto.book.DetailedBookDTO;
import com.cgdpd.library.dto.book.SearchBookCriteria;
import com.cgdpd.library.dto.pagination.PagedResponse;
import com.cgdpd.library.dto.pagination.PaginationCriteria;
import com.cgdpd.library.dto.pagination.SortParam;
import com.cgdpd.library.entity.BookEntity;
import com.cgdpd.library.dto.book.DetailedBookDTO;
import com.cgdpd.library.exceptions.NotFoundException;
import com.cgdpd.library.mapper.BookMapper;
import com.cgdpd.library.model.book.Book;
import com.cgdpd.library.repository.BookRepository;
import com.cgdpd.library.repository.specification.BookSpecifications;
import com.cgdpd.library.type.Isbn13;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public PagedResponse<Book> getBooks(PaginationCriteria paginationCriteria,
                                        SearchBookCriteria searchCriteria) {
        Sort sort = paginationCriteria.sort()
              .map(SortParam::toSort)
              .orElse(Sort.unsorted());
        var pageable = PageRequest.of(paginationCriteria.pageIndex(), paginationCriteria.pageSize(),
              sort);
        Page<BookEntity> books = bookRepository.findAll(
              BookSpecifications.byBookSearchCriteria(searchCriteria), pageable);
        PagedResponse<DetailedBookDTO> response = new PagedResponse<>(
              books.map(bookMapper::mapToDetailedBookDto).getContent(), books.getNumber(),
              books.getSize(),
              books.getTotalElements());
        return response;

    public DetailedBookDTO getDetailedBookByIsbn13(Isbn13 isbn13) {
        return findDetailedBookByIsbn13(isbn13).orElseThrow(
              () -> new NotFoundException(String.format("No book by the isbn %s", isbn13.value())));
    }

    public Optional<DetailedBookDTO> findDetailedBookByIsbn13(Isbn13 isbn13) {
        return bookRepository.findDetailedBookByIsbn(isbn13.value())
              .map(bookMapper::mapToDetailedBookDto);
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
