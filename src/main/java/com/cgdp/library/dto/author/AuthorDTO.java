package com.cgdp.library.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthorDTO(@NotNull Long id, @NotBlank String name) {}
