package com.cgdp.library.dto.author;

import static com.cgdp.library.validation.Validator.requiredNotNullOrBlank;

public record CreateAuthorRequestDTO(String authorName) {

    public CreateAuthorRequestDTO(String authorName) {
        this.authorName = requiredNotNullOrBlank("authorName", authorName);
    }
}
