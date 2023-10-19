package com.cgdpd.library.common.pagination;

import static com.cgdpd.library.common.validation.Validator.required;

public record SortParams(String field,
                         Direction direction) {

    public SortParams {
        required("field", field);
        required("direction", direction);
    }

    public static SortParams of(String field, Direction direction) {
        return new SortParams(field, direction);
    }

    public enum Direction {
        ASC, DESC;

        public static Direction defaultDirection() {
            return ASC;
        }
    }
}
