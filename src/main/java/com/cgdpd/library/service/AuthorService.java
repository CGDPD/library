package com.cgdpd.library.service;

import com.cgdpd.library.entity.AuthorEntity;
import com.cgdpd.library.model.author.Author;
import com.cgdpd.library.repository.AuthorRepository;
import com.cgdpd.library.type.AuthorId;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public Author createAuthor(String authorName) {
        var authorEntity = new AuthorEntity();
        authorEntity.setName(authorName);
        var savedAuthor = authorRepository.save(authorEntity);
        return new Author(AuthorId.of(savedAuthor.getId()), savedAuthor.getName());
    }

    @Transactional
    public boolean authorExist(AuthorId authorId) {
        return authorRepository.existsById(authorId.value());
    }
}
