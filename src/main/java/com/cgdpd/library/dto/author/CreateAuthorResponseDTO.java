package com.cgdpd.library.dto.author;

import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.requiredNotBlank;

import com.cgdpd.library.type.AuthorId;

public record CreateAuthorResponseDTO(AuthorId id, String authorName) {

    public CreateAuthorResponseDTO(AuthorId id, String authorName) {
        this.id = required("id", id);
        this.authorName = requiredNotBlank("authorName", authorName);
    }
}
