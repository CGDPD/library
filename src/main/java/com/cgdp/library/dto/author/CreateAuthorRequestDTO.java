package com.cgdp.library.dto.author;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreateAuthorRequestDTO(@Valid @NotNull String name) {}
