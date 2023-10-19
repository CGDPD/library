package com.cgdpd.library.catalog.domain.book.dto;

import org.springframework.util.MultiValueMap;

public interface QueryParamsConvertible {

    MultiValueMap<String, String> toQueryParams();
}
