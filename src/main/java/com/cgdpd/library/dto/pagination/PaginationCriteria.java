package com.cgdpd.library.dto.pagination;

import static com.cgdpd.library.util.OptionalUtil.actualOrEmpty;
import static com.cgdpd.library.validation.Validator.requiredNotNegative;
import static com.cgdpd.library.validation.Validator.requiredPositive;

import lombok.Builder;

import java.util.Optional;

@Builder
public record PaginationCriteria(int pageIndex,
                                 int pageSize,
                                 Optional<SortParam> sort) {

    public PaginationCriteria(int pageIndex,
                              int pageSize,
                              Optional<SortParam> sort) {
        this.pageIndex = requiredPositive("pageIndex", pageIndex);
        this.pageSize = requiredNotNegative("pageSize", pageSize);
        this.sort = actualOrEmpty(sort);
    }
}
