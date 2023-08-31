package com.cgdpd.library.dto.pagination;

import static com.cgdpd.library.BookTestData.CHRIS_DANE__ONCE_AGAIN__2022;
import static com.cgdpd.library.BookTestData.COLUMBUS_CHRISTOPHER__IN_LOVE__2011;
import static com.cgdpd.library.BookTestData.JANE_DANE__SURVIVOR__2007;
import static com.cgdpd.library.BookTestData.JOHN_DOE__THE_ADVENTUROUS__1987;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.cgdpd.library.entity.BookEntity;
import com.cgdpd.library.repository.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class SortParamTest {

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_return_paginated_books_sorted_by_title() {
        // given
        int page = 0;
        int size = 2;
        var sortParam = new SortParam("title", SortParam.Direction.ASC);
        var pageable = PageRequest.of(page, size, sortParam.toDomainSort());

        when(bookRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(
              COLUMBUS_CHRISTOPHER__IN_LOVE__2011,
              JOHN_DOE__THE_ADVENTUROUS__1987
        )));

        // when
        Page<BookEntity> books = bookRepository.findAll(pageable);

        // then
        assertThat(books.getTotalElements()).isEqualTo(2);
        assertThat(books.getTotalPages()).isEqualTo(1);
        assertThat(books.getContent())
              .containsExactly(COLUMBUS_CHRISTOPHER__IN_LOVE__2011,
                    JOHN_DOE__THE_ADVENTUROUS__1987);
    }

    @Test
    void should_return_paginated_books_sorted_by_publicationYear() {
        // given
        int pageIndex = 0;
        int pageSize = 10;
        var sortParam = new SortParam("publicationYear", SortParam.Direction.DESC);
        var pageable = PageRequest.of(pageIndex, pageSize, sortParam.toDomainSort());

        when(bookRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(
              JOHN_DOE__THE_ADVENTUROUS__1987,
              JANE_DANE__SURVIVOR__2007,
              COLUMBUS_CHRISTOPHER__IN_LOVE__2011,
              CHRIS_DANE__ONCE_AGAIN__2022
        )));

        // when
        Page<BookEntity> books = bookRepository.findAll(pageable);

        // then
        assertThat(books.getTotalElements()).isEqualTo(4);
        assertThat(books.getTotalPages()).isEqualTo(1);
        assertThat(books.getContent())
              .containsExactly(JOHN_DOE__THE_ADVENTUROUS__1987, JANE_DANE__SURVIVOR__2007,
                    COLUMBUS_CHRISTOPHER__IN_LOVE__2011, CHRIS_DANE__ONCE_AGAIN__2022);
    }
}
