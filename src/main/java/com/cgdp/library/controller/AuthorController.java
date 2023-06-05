package com.cgdp.library.controller;

import com.cgdp.library.converters.AuthorDtoConverter;
import com.cgdp.library.dto.CreateAuthorRequestDTO;
import com.cgdp.library.entity.AuthorEntity;
import com.cgdp.library.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/authors")
@RestController
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorDtoConverter authorDtoConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorEntity createAuthor(@RequestBody CreateAuthorRequestDTO createAuthorRequestDTO) {

        AuthorEntity authorEntity = authorDtoConverter.toEntity(createAuthorRequestDTO);
        AuthorEntity createdAuthor = authorService.save(authorEntity);
        return createdAuthor;
    }

}
