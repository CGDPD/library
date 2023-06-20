package com.cgdpd.library;

import com.cgdp.library.entity.AuthorEntity;

public class AuthorTestData {

    public static AuthorEntity.AuthorEntityBuilder anAuthor() {
        return anAuthor(1L)
              .name("J. R. R. Tolkien");
    }

    public static AuthorEntity.AuthorEntityBuilder anAuthor(Long id) {
        return AuthorEntity.builder()
              .id(id)
              .name("J. R. R. Tolkien");
    }
}
