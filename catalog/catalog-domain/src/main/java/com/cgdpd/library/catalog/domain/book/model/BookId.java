package com.cgdpd.library.catalog.domain.book.model;

import com.cgdpd.library.types.IdType;
import com.cgdpd.library.types.serializer.LongIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = LongIdSerializer.class)
public class BookId extends IdType {

    public BookId(Long id) {
        super(id);
    }

    public static BookId of(Long id) {
        return new BookId(id);
    }
}
