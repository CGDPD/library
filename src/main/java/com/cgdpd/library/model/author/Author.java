package com.cgdpd.library.model.author;

import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.requiredNotBlank;

import com.cgdpd.library.type.AuthorId;
import lombok.Builder;

@Builder
public record Author(AuthorId id, String name) {

    public Author(AuthorId id, String name) {
        this.id = required("id", id);
        this.name = requiredNotBlank("name", name);
    }
}
