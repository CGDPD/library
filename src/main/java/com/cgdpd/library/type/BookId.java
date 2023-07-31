package com.cgdpd.library.type;

import com.cgdpd.library.json.LongIdSerializer;
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
