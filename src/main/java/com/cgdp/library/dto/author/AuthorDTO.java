package com.cgdp.library.dto.author;

import static com.cgdp.library.validation.Validator.required;
import static com.cgdp.library.validation.Validator.requiredNotBlank;

public record AuthorDTO(Long id, String name) {

    public AuthorDTO(Long id, String name) {
        this.id = required("id", id);
        this.name = requiredNotBlank("name", name);
    }
}
