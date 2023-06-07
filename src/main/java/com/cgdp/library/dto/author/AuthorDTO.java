package com.cgdp.library.dto.author;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AuthorDTO(@Valid @ NotNull Long id, @Valid @NotNull String name) {}
