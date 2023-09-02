package com.cgdpd.library.types;


import static com.cgdpd.library.common.validation.Validator.requiredValidIsbn13;

import java.util.Random;

public record Isbn13(String value) {

    public Isbn13(String value) {
        this.value = requiredValidIsbn13("value", value);
    }

    public static Isbn13 of(String value) {
        return new Isbn13(value);
    }

    public static Isbn13 random() {
        var isbn = new int[13];
        isbn[0] = 9;
        isbn[1] = 7;
        isbn[2] = 8;

        var random = new Random();
        for (var i = 3; i < 12; i++) {
            isbn[i] = random.nextInt(10);
        }

        var sum = 0;
        for (var i = 0; i < 12; i++) {
            sum += ((i % 2 == 0) ? 1 : 3) * isbn[i];
        }
        isbn[12] = (10 - (sum % 10)) % 10;

        var sb = new StringBuilder();
        for (var i : isbn) {
            sb.append(i);
        }
        return new Isbn13(sb.toString());
    }
}
