package com.cgdpd.library.dto.author;

import static com.cgdpd.library.validation.Validator.requiredNotBlank;

public record CreateAuthorRequestDTO(String authorName) {

    public CreateAuthorRequestDTO(String authorName) {
        this.authorName = requiredNotBlank("authorName", authorName);
    }
}
