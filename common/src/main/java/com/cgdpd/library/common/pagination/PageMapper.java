package com.cgdpd.library.common.pagination;

import static org.springframework.data.domain.Sort.unsorted;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageMapper {

    public static PageRequest paginationCriteriaToPageRequest(PaginationCriteria paginationCriteria) {
        // TODO: 19/09/2023 Client can send wrong field to sort by, need to handle that error
        var sort = paginationCriteria.sortParams().map(it ->
                    Sort.by(it.direction() == SortParams.Direction.ASC
                                ? Sort.Direction.ASC
                                : Sort.Direction.DESC,
                          it.field()))
              .orElse(unsorted());
        return PageRequest.of(paginationCriteria.pageIndex(), paginationCriteria.pageSize(), sort);
    }
}
