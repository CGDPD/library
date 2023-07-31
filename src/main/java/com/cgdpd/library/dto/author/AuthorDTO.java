package com.cgdpd.library.dto.author;

import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.requiredNotBlank;

import com.cgdpd.library.type.AuthorId;

public record AuthorDTO(AuthorId id, String name) {

    public AuthorDTO(AuthorId id, String name) {
        this.id = required("id", id);
        this.name = requiredNotBlank("name", name);
    }
}
