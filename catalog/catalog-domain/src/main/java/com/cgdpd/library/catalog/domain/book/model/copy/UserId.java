package com.cgdpd.library.catalog.domain.book.model.copy;

import com.cgdpd.library.types.IdType;
import com.cgdpd.library.types.serializer.LongIdSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = LongIdSerializer.class)
public class UserId extends IdType {

    public UserId(Long id) {
        super(id);
    }

    public static UserId of(Long id) {
        return new UserId(id);
    }
}
