package com.cgdpd.library.dto.book;

import org.springframework.data.domain.Sort;

public record SearchBookRequest(SearchBookCriteria criteria, int pageIndex, int pageSize,
                                Sort sort) {
    public SearchBookRequest(SearchBookCriteria criteria, int pageIndex, int pageSize) {
        this(criteria, pageIndex, pageSize, null);
    }
}


