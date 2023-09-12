package com.cgdpd.library.catalog.domain.author;

import static com.cgdpd.library.common.validation.Validator.required;
import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;

import lombok.Builder;

@Builder
public record Author(AuthorId id, String name) {

    public Author(AuthorId id, String name) {
        this.id = required("id", id);
        this.name = requiredNotBlank("name", name);
    }
}
