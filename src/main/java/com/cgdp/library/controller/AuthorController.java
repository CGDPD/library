package com.cgdp.library.controller;

import com.cgdp.library.dto.author.CreateAuthorResponseDTO;
import com.cgdp.library.dto.author.CreateAuthorRequestDTO;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAuthorResponseDTO createAuthor(@RequestBody CreateAuthorRequestDTO createAuthorRequestDTO) {
        String authorName = createAuthorRequestDTO.name();
        return authorService.createAuthor(authorName);
    }


}
