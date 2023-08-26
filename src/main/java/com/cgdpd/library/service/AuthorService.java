package com.cgdpd.library.service;

import com.cgdpd.library.dto.author.AuthorDTO;
import com.cgdpd.library.entity.AuthorEntity;
import com.cgdpd.library.repository.AuthorRepository;
import com.cgdpd.library.type.AuthorId;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public AuthorDTO createAuthor(String authorName) {
        var authorEntity = new AuthorEntity();
        authorEntity.setName(authorName);
        var savedAuthor = authorRepository.save(authorEntity);
        return new AuthorDTO(AuthorId.of(savedAuthor.getId()), savedAuthor.getName());
    }

    @Transactional
    public boolean authorExist(AuthorId authorId) {
        return authorRepository.existsById(authorId.value());
    }

    @Transactional(readOnly = true)
    public Page<AuthorDTO> getAuthors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AuthorEntity> authors = authorRepository.findAll(pageable);
        return authors.map(author -> new AuthorDTO(AuthorId.of(author.getId()), author.getName()));
    }
}
