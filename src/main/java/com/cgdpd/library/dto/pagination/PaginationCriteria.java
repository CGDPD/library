package com.cgdpd.library.dto.pagination;

import static com.cgdpd.library.util.OptionalUtil.actualOrEmpty;
import static com.cgdpd.library.validation.Validator.requiredNotNegative;
import static com.cgdpd.library.validation.Validator.requiredPositive;

import lombok.Builder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@Builder
public record PaginationCriteria(int pageIndex,
                                 int pageSize,
                                 Optional<SortParams> sort) {

    public PaginationCriteria(int pageIndex,
                              int pageSize,
                              Optional<SortParams> sort) {
        this.pageIndex = requiredPositive("pageIndex", pageIndex);
        this.pageSize = requiredNotNegative("pageSize", pageSize);
        this.sort = actualOrEmpty(sort);
    }

    public PageRequest toPageRequest() {
        var sort = this.sort()
              .map(SortParams::toDomainSort)
              .orElse(Sort.unsorted());
        return PageRequest.of(this.pageIndex, this.pageSize, sort);
    }
}
