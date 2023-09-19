package com.cgdpd.library.catalog.app.controller;

import com.cgdpd.library.catalog.app.service.AuthorService;
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
@RestController
@RequestMapping("/author")
public class AuthorController {

}
