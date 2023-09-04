package com.cgdpd.library.dto.pagination;

import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.requiredNotNegative;

import lombok.Builder;

import java.util.List;

@Builder
public record PagedResponse<T>(List<T> content,
                               int pageNumber,
                               int pageSize,
                               long totalElements,
                               int totalPages) {

    public PagedResponse {
        required("content", content);
        requiredNotNegative("pageNumber", pageNumber);
        requiredNotNegative("pageSize", pageSize);
        requiredNotNegative("totalElements", totalElements);
        requiredNotNegative("totalPages", totalPages);
    }
}
