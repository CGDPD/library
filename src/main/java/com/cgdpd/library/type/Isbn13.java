package com.cgdpd.library.type;

import static com.cgdpd.library.validation.Validator.requiredValidIsbn;

public record Isbn13(String value) {

    public Isbn13(String value) {
        this.value = requiredValidIsbn("value", value);
    }

    public static Isbn13 of(String value) {
        return new Isbn13(value);
    }
}
