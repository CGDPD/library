package com.cgdp.library.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.cgdp.library.validation.ValidationUtility.required;
import static com.cgdp.library.validation.ValidationUtility.requiredNotBlank;

public record AuthorDTO(@NotNull Long id, @NotBlank String name) {

    public AuthorDTO(@NotNull Long id, String name) {

        this.id = required("id", id);
        this.name = requiredNotBlank("name", name);
    }
}
