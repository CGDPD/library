package com.cgdp.library.dto.author;

import jakarta.validation.constraints.NotBlank;

public record CreateAuthorRequestDTO(@NotBlank String name) {}
