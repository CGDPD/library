package com.cgdp.library.controller;

import com.cgdp.library.converters.AuthorDtoConverter;
import com.cgdp.library.dto.AuthorDTO;
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

    private AuthorService authorService;
    private AuthorDtoConverter authorDtoConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO newAuthor(@RequestBody AuthorDTO authorDTO) {

        AuthorEntity newAuthor = authorDtoConverter.toEntity(authorDTO);
        AuthorEntity saveAuthor = authorService.save(newAuthor);

        return authorDtoConverter.toDTO(saveAuthor);
    }
}
