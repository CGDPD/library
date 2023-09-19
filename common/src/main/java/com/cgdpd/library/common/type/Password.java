package com.cgdpd.library.common.type;

import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;

import com.cgdpd.library.common.type.serializer.TypeSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = TypeSerializer.class)
public class Password extends Type<String> {

    private Password(String password) {
        super(requiredNotBlank("password", password));
    }

    public static Password of(String value) {
        return new Password(value);
    }

    @Override
    public String toString() {
        return "<masked>";
    }
}
