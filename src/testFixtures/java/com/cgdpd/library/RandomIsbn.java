package com.cgdpd.library;

import java.util.Random;

public class RandomIsbn {

    private static final Random random = new Random();

    public static String generateISBN13() {
        int[] isbn = new int[13];
        isbn[0] = 9;
        isbn[1] = 7;
        isbn[2] = 8;

        for (int i = 3; i < 12; i++) {
            isbn[i] = random.nextInt(10);
        }

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += ((i % 2 == 0) ? 1 : 3) * isbn[i];
        }
        isbn[12] = (10 - (sum % 10)) % 10;

        StringBuilder sb = new StringBuilder();
        for (int i : isbn) {
            sb.append(i);
        }
        return sb.toString();
    }
}
