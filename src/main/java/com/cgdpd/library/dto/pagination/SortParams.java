package com.cgdpd.library.dto.pagination;

import static com.cgdpd.library.validation.Validator.required;

import org.springframework.data.domain.Sort;

public record SortParams(String field,
                         Direction direction) {

    public SortParams(String field, Direction direction) {
        this.field = required("field", field);
        this.direction = required("order", direction);
    }

    public Sort toDomainSort() {
        return Sort.by(
              this.direction == Direction.ASC ? Sort.Direction.ASC : Sort.Direction.DESC,
              this.field
        );
    }

    public enum Direction {
        ASC, DESC
    }
}
