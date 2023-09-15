package com.cgdpd.library.catalog.app.service;

import com.cgdpd.library.catalog.app.entity.AuthorEntity;
import com.cgdpd.library.catalog.app.repository.AuthorRepository;
import com.cgdpd.library.catalog.domain.author.Author;
import com.cgdpd.library.catalog.domain.author.AuthorId;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public Author createAuthor(String authorName) {
        var authorEntity = new AuthorEntity();
        authorEntity.setName(authorName);
        var savedAuthor = authorRepository.save(authorEntity);
        return new Author(AuthorId.of(savedAuthor.getId()), savedAuthor.getName());
    }

    public boolean authorExist(AuthorId authorId) {
        return authorRepository.existsById(authorId.value());
    }
}
