package com.cgdpd.library.common.pagination;

import static com.cgdpd.library.common.util.OptionalUtil.actualOrEmpty;
import static com.cgdpd.library.common.validation.Validator.requiredNotNegative;
import static com.cgdpd.library.common.validation.Validator.requiredPositive;

import com.cgdpd.library.common.pagination.SortParams.Direction;

import lombok.Builder;

import java.util.Optional;

@Builder
public record PaginationCriteria(int pageIndex,
                                 int pageSize,
                                 Optional<String> sort,
                                 Optional<String> direction) {

    public PaginationCriteria(int pageIndex,
                              int pageSize,
                              Optional<String> sort,
                              Optional<String> direction) {
        this.pageIndex = requiredNotNegative("pageIndex", pageIndex);
        this.pageSize = requiredPositive("pageSize", pageSize);
        this.sort = actualOrEmpty(sort);
        this.direction = actualOrEmpty(direction);
    }

    public Optional<SortParams> sortParams() {
        return sort.map(it -> SortParams.of(it,
              direction.map(dir -> Direction.valueOf(dir.toUpperCase()))
                    .orElseGet(Direction::defaultDirection)));
    }
}
