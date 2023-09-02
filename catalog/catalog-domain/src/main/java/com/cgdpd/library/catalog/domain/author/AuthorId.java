package com.cgdpd.library.catalog.domain.author;

import com.cgdpd.library.types.IdType;
import com.cgdpd.library.types.serializer.LongIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = LongIdSerializer.class)
public class AuthorId extends IdType {

    public AuthorId(Long id) {
        super(id);
    }

    public static AuthorId of(Long id) {
        return new AuthorId(id);
    }
}
