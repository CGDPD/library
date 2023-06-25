package com.cgdp.library.dto.author;

import static com.cgdp.library.validation.Validator.required;
import static com.cgdp.library.validation.Validator.requiredNotNullOrBlank;

public record CreateAuthorResponseDTO(Long id, String authorName) {

    public CreateAuthorResponseDTO(Long id, String authorName) {
        this.id = required("id", id);
        this.authorName = requiredNotNullOrBlank("authorName", authorName);
    }
}
