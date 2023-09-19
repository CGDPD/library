package com.cgdpd.library.catalog.domain.book.model.copy;

import com.cgdpd.library.common.type.IdType;
import com.cgdpd.library.common.type.serializer.LongIdSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = LongIdSerializer.class)
public class BookCopyId extends IdType {

    public BookCopyId(Long id) {
        super(id);
    }

    public static BookCopyId of(Long id) {
        return new BookCopyId(id);
    }
}
