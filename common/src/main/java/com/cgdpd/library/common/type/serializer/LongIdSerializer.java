package com.cgdpd.library.common.type.serializer;

import com.cgdpd.library.common.type.IdType;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class LongIdSerializer<T extends IdType> extends JsonSerializer<T> {

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider serializers)
          throws IOException {
        gen.writeNumber(value.value());
    }
}
