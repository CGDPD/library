package com.cgdpd.library.dto.pagination;

import com.cgdpd.library.validation.Validator;

import lombok.Builder;

import java.util.List;

@Builder
public record PagedResponse<T>(List<T> content,
                               int pageNumber,
                               int pageSize,
                               long totalElements) {

    public PagedResponse {
        Validator.required("content", content);
        Validator.requiredPositive("pageNumber", pageNumber);
        Validator.requiredNotNegative("pageSize", pageSize);
        Validator.requiredNotNegative("totalElements", totalElements);
    }

    public int totalPages() {
        return (int) Math.ceil((double) totalElements / (double) pageSize);
    }
}
