package com.cgdp.library.service;

import com.cgdp.library.dto.author.CreateAuthorResponseDTO;
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
    public CreateAuthorResponseDTO createAuthor(String authorName) {

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName(authorName);
        AuthorEntity savedAuthor = authorRepository.save(authorEntity);
        return new CreateAuthorResponseDTO(savedAuthor.getId(), savedAuthor.getName());
    }
}
