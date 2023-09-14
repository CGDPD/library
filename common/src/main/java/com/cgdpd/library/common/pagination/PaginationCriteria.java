package com.cgdpd.library.common.pagination;

import static com.cgdpd.library.common.util.OptionalUtil.actualOrEmpty;
import static com.cgdpd.library.common.validation.Validator.requiredNotNegative;
import static com.cgdpd.library.common.validation.Validator.requiredPositive;

import lombok.Builder;

import java.util.Optional;

@Builder
public record PaginationCriteria(int pageIndex,
                                 int pageSize,
                                 Optional<SortParams> sort) {

    public PaginationCriteria(int pageIndex,
                              int pageSize,
                              Optional<SortParams> sort) {
        this.pageIndex = requiredNotNegative("pageIndex", pageIndex);
        this.pageSize = requiredPositive("pageSize", pageSize);
        this.sort = actualOrEmpty(sort);
    }
}
