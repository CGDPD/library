package com.cgdp.library.service;

import com.cgdp.library.entity.AuthorEntity;
import com.cgdp.library.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthorService {

    private AuthorRepository authorRepository;

    @Transactional
    public AuthorEntity save(AuthorEntity author) {

        return authorRepository.save(author);
    }
}
