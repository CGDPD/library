package com.cgdpd.library.catalog.domain.author.dto;

import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;

public record CreateAuthorRequestDto(String authorName) {

    public CreateAuthorRequestDto(String authorName) {
        this.authorName = requiredNotBlank("authorName", authorName);
    }
}
