package com.cgdp.library.service;

import com.cgdp.library.dto.author.AuthorDTO;
import com.cgdp.library.entity.AuthorEntity;
import com.cgdp.library.exceptions.NotFoundException;
import com.cgdp.library.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthorService {

    private AuthorRepository authorRepository;

    @Transactional
    public AuthorDTO createAuthor(String authorName) {

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName(authorName);
        AuthorEntity savedAuthor = authorRepository.save(authorEntity);
        return new AuthorDTO(savedAuthor.getId(), savedAuthor.getName());
    }

    @Transactional
    public boolean doesAuthorExist(Long authorId) {
        return authorRepository.existsById(authorId);
    }
}
