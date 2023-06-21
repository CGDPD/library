package com.cgdp.library.dto.author;

import jakarta.validation.constraints.NotBlank;

import static com.cgdp.library.validation.ValidationUtility.requiredNotBlank;

public record CreateAuthorRequestDTO(@NotBlank String name) {

    public CreateAuthorRequestDTO(String name) {

        this.name = requiredNotBlank("name", name);
    }
}
