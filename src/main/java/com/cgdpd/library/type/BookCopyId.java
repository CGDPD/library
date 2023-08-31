package com.cgdpd.library.type;

import com.cgdpd.library.json.LongIdSerializer;

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
