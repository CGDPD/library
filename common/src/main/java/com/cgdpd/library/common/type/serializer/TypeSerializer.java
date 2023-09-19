package com.cgdpd.library.common.type.serializer;

import com.cgdpd.library.common.type.Type;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TypeSerializer<T extends Type<?>> extends JsonSerializer<T> {
    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider serializers)
          throws IOException {
        gen.writeString(value.toString());
    }
}
