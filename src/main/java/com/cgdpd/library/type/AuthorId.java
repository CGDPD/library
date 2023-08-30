package com.cgdpd.library.type;

import com.cgdpd.library.json.LongIdSerializer;

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
