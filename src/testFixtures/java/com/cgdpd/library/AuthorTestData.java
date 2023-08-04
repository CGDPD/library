package com.cgdpd.library;

import com.cgdpd.library.dto.author.AuthorDTO;
import com.cgdpd.library.entity.AuthorEntity;
import com.cgdpd.library.type.AuthorId;
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


    public static AuthorDTO.AuthorDTOBuilder anAuthor() {
        return anAuthor(1L);
    }

    public static AuthorDTO.AuthorDTOBuilder anAuthor(Long id) {
        return AuthorDTO.builder()
              .id(AuthorId.of(id))
              .name("J. R. R. Tolkien");
    }

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
