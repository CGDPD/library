package com.cgdpd.library.catalog.api.controller;

import com.cgdpd.library.catalog.api.service.AuthorService;
import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.dto.CreateAuthorRequestDto;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/author")
@RestController
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author createAuthor(@RequestBody CreateAuthorRequestDto createAuthorRequestDto) {
        var authorName = createAuthorRequestDto.authorName();
        return authorService.createAuthor(authorName);
    }
}