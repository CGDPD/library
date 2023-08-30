package com.cgdpd.library.dto.pagination;

import org.springframework.data.domain.Sort;

public record SortParam(String field, Order order) {
    public enum Order {
        ASC, DESC
    }

    public Sort toSort() {
        return Sort.by(
              this.order == Order.ASC ? Sort.Direction.ASC : Sort.Direction.DESC,
              this.field
        );
    }
}
