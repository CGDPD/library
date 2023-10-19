package com.cgdpd.library.common.pagination;

import static com.cgdpd.library.common.util.OptionalUtil.actualOrEmpty;
import static com.cgdpd.library.common.validation.Validator.requiredNotNegative;
import static com.cgdpd.library.common.validation.Validator.requiredPositive;

import com.cgdpd.library.common.pagination.SortParams.Direction;

import lombok.Builder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

@Builder
public record PaginationCriteria(int pageIndex,
                                 int pageSize,
                                 Optional<String> sort,
                                 Optional<String> direction) implements QueryParamsConvertible {

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

    @Override
    public MultiValueMap<String, String> toQueryParams() {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("pageIndex", String.valueOf(pageIndex));
        queryParams.add("pageSize", String.valueOf(pageSize));
        sort.ifPresent(value -> queryParams.add("sort", value));
        direction.ifPresent(value -> queryParams.add("direction", value));
        return queryParams;
    }
}
