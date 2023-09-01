package com.cgdpd.library.dto.pagination;

import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.requiredNotNegative;
import static com.cgdpd.library.validation.Validator.requiredPositive;

import lombok.Builder;

import java.util.List;

@Builder
public record PagedResponse<T>(List<T> content,
                               int pageNumber,
                               int pageSize,
                               long totalElements) {

    public PagedResponse {
        required("content", content);
        requiredPositive("pageNumber", pageNumber);
        requiredNotNegative("pageSize", pageSize);
        requiredNotNegative("totalElements", totalElements);
    }

    public int totalPages() {
        return (int) Math.ceil((double) totalElements / (double) pageSize);
    }
}
