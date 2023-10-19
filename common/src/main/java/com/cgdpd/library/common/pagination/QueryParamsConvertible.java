package com.cgdpd.library.common.pagination;

import org.springframework.util.MultiValueMap;

public interface QueryParamsConvertible {

    MultiValueMap<String, String> toQueryParams();
}
