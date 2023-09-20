package com.cgdpd.library.catalog.app.service;

import static com.cgdpd.library.catalog.app.repository.specification.BookSpecifications.byBookSearchCriteria;
import static com.cgdpd.library.common.pagination.PageMapper.paginationCriteriaToPageRequest;

import com.cgdpd.library.catalog.app.mapper.BookMapper;
import com.cgdpd.library.catalog.app.repository.BookRepository;
import com.cgdpd.library.catalog.domain.book.dto.CreateBookRequestDto;
import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.dto.SearchBookCriteria;
import com.cgdpd.library.catalog.domain.book.model.Book;
import com.cgdpd.library.common.exception.NotFoundException;
import com.cgdpd.library.common.pagination.PagedResponse;
import com.cgdpd.library.common.pagination.PaginationCriteria;
import com.cgdpd.library.common.type.Isbn13;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;

    @Transactional
    public Book createBook(CreateBookRequestDto createBookRequestDto) {
        if (!authorService.authorExist(createBookRequestDto.authorId())) {
            throw new NotFoundException(
                  "Author with id " + createBookRequestDto.authorId() + " not found");
        }
        var bookEntity = bookMapper.mapToBookEntity(createBookRequestDto);
        var createdBook = bookRepository.save(bookEntity);
        return bookMapper.mapToBook(createdBook);
    }

    public DetailedBookDto getDetailedBookByIsbn13(Isbn13 isbn13) {
        return findDetailedBookByIsbn13(isbn13).orElseThrow(
              () -> new NotFoundException(String.format("No book by the isbn %s", isbn13.value())));
    }

    public Optional<DetailedBookDto> findDetailedBookByIsbn13(Isbn13 isbn13) {
        return bookRepository.findDetailedBookByIsbn(isbn13.value())
              .map(bookMapper::mapToDetailedBookDto);
    }

    public PagedResponse<DetailedBookDto> findDetailedBooksPage(
          PaginationCriteria paginationCriteria, SearchBookCriteria searchCriteria) {
        var pageRequest = paginationCriteriaToPageRequest(paginationCriteria);
        var booksEntitiesPage = bookRepository.findAll(
              byBookSearchCriteria(searchCriteria),
              pageRequest);
        return PagedResponse.<DetailedBookDto>builder()
              .content(booksEntitiesPage.map(bookMapper::mapToDetailedBookDto).getContent())
              .pageNumber(booksEntitiesPage.getNumber())
              .pageSize(booksEntitiesPage.getSize())
              .totalElements(booksEntitiesPage.getTotalElements())
              .totalPages(booksEntitiesPage.getTotalPages())
              .build();
    }
}
