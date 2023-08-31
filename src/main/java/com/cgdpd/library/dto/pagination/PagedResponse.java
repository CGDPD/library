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
        Validator.required("pageNumber", pageNumber);
        Validator.required("pageSize", pageSize);
        Validator.required("totalElements", totalElements);
        this.totalPages = (int) Math.ceil((double) totalElements / (double) pageSize);
    }

    private static int totalPages;

    public static int getTotalPages() {
        return totalPages;
    }
}

