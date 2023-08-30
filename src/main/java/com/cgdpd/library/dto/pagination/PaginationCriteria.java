package com.cgdpd.library.dto.pagination;

import java.util.Optional;

public record PaginationCriteria(int pageIndex, int pageSize, Optional<SortParam> sort) {
}
