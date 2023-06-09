package com.cgdpd.library.validation;

class IsbnValidator {

    static boolean isValidIsbn(String isbn) {
        String cleanISBN = isbn.replaceAll("[^0-9]", "");
        int length = cleanISBN.length();

        if (length != 13) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = cleanISBN.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        int checkDigit = cleanISBN.charAt(12) - '0';
        return (10 - (sum % 10)) % 10 == checkDigit;
    }
}
