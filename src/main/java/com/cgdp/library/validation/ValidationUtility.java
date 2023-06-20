package com.cgdp.library.validation;

import java.time.LocalDate;
import java.util.function.Supplier;

public class ValidationUtility {

    public static Long required(String paramName, Long value) {
        validate(() -> value == null, "%s must not be null", paramName);
        return value;
    }

    public static String requiredNotBlank(String paramName, String value) {

        validate(() -> value == null, "%s must not be blank", paramName);
        return value;
    }

    public static LocalDate validatePublicationYear(String paramName, LocalDate value) {

        validate(() -> value.isAfter(LocalDate.now()), "Invalid %s", paramName);
        return value;
    }

    public static String validateISBN(String paramName, String value) {

        String cleanISBN = value.replaceAll("[^0-9]", "");
        int length = cleanISBN.length();

        validate(
              () -> value == null || value.isBlank() || length != 13 || !isValidISBN13(cleanISBN),
              "%s is not a valid ISBN", paramName);
        return value;
    }

    private static boolean isValidISBN13(String isbn) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        int checkDigit = isbn.charAt(12) - '0';

        return (10 - (sum % 10)) % 10 == checkDigit;
    }

    public static void validate(Supplier<Boolean> validation, String format, Object... params) {
        if (validation.get()) {
            throw new IllegalArgumentException(String.format(format, params));
        }
    }
}
