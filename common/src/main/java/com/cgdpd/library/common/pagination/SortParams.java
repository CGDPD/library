package com.cgdpd.library.common.pagination;

import static com.cgdpd.library.common.validation.Validator.required;

public record SortParams(String field,
                         Direction direction) {

    public SortParams(String field, Direction direction) {
        this.field = required("field", field);
        this.direction = required("direction", direction);
    }

    public enum Direction {
        ASC, DESC
    }
}
