package com.cgdpd.library.catalog.api;

import com.cgdpd.library.catalog.api.entity.AuthorEntity;

import java.util.concurrent.atomic.AtomicLong;

public class AuthorTestData {


    private static final AtomicLong authorId = new AtomicLong(1L);
    public static final AuthorEntity AUTHOR_JOHN_DOE = AuthorEntity.builder()
          .id(authorId.getAndIncrement())
          .name("John Doe")
          .build();
    public static final AuthorEntity AUTHOR_JANE_DANE = AuthorEntity.builder()
          .id(authorId.getAndIncrement())
          .name("Jane Dane")
          .build();
    public static final AuthorEntity AUTHOR_CHRIS_DANE = AuthorEntity.builder()
          .id(authorId.getAndIncrement())
          .name("Chris Dane")
          .build();
    public static final AuthorEntity AUTHOR_COLUMBUS_CHRISTOPHER = AuthorEntity.builder()
          .id(authorId.getAndIncrement())
          .name("Columbus Christopher")
          .build();


    public static AuthorEntity.AuthorEntityBuilder anAuthorEntity() {
        return anAuthorEntity(1L)
              .name("J. R. R. Tolkien");
    }

    public static AuthorEntity.AuthorEntityBuilder anAuthorEntity(Long id) {
        return AuthorEntity.builder()
              .id(id)
              .name("J. R. R. Tolkien");
    }
}
