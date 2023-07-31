package com.cgdpd.library.type;

import com.cgdpd.library.json.LongIdSerializer;
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
