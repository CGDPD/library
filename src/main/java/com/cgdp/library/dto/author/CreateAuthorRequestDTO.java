package com.cgdp.library.dto.author;

import static com.cgdp.library.validation.Validator.requiredNotBlank;

public record CreateAuthorRequestDTO(String authorName) {

    public CreateAuthorRequestDTO(String authorName) {
        this.authorName = requiredNotBlank("authorName", authorName);
    }
}
