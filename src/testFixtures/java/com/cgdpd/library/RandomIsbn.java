package com.cgdpd.library;

import com.cgdpd.library.type.Isbn13;

import java.util.Random;

public class RandomIsbn {

    private static final Random random = new Random();

    public static Isbn13 generateISBN13() {
        var isbn = new int[13];
        isbn[0] = 9;
        isbn[1] = 7;
        isbn[2] = 8;

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
        return Isbn13.of(sb.toString());
    }
}
