package com.cgdpd.library.common.validation;


import com.cgdpd.library.common.exception.ValidationException;

import java.time.LocalDate;
import java.time.temporal.ValueRange;
import java.util.Optional;
import java.util.function.Supplier;

public final class Validator {

    private Validator() {
    }

    public static <T> T required(String paramName, T value) {
        validate(() -> value == null, "%s must not be null", paramName);
        return value;
    }

    public static String requiredNotBlank(String paramName, String value) {
        validate(() -> value == null || value.isBlank(), "%s must not be null or blank", paramName);
        return value;
    }

    public static <T extends Number> T requiredPositive(String paramName, T value) {
        validate(() -> value == null || value.intValue() <= 0,
              "%s must not be null, must be a positive number", paramName);
        return value;
    }

    public static <T extends Number> T requiredNotNegative(String paramName, T value) {
        validate(() -> value == null || value.intValue() < 0, "%s must not be null or negative",
              paramName);
        return value;
    }

    public static Optional<Short> checkYearNotFuture(String paramName, Optional<Short> value) {
        value.ifPresent(year -> {
            var currentYear = LocalDate.now().getYear();
            validate(() -> year > currentYear,
                  "Invalid %s. %s is after the current year", paramName, year);
        });
        return value;
    }

    public static String requiredValidIsbn13(String paramName, String value) {
        validate(() -> value == null || value.isBlank() || !IsbnValidator.isValidIsbn13(value),
              "%s must not be null or blank, %s is not a valid ISBN. An ISBN must have 13 numbers and have a valid check number",
              paramName, value);
        return value;
    }

    public static Integer requiredValidHttpStatus(Integer value) {
        validate(() -> value == null || !ValueRange.of(100, 599).isValidValue(value),
              "%s is not a valid http status",
              value);
        return value;
    }

    public static void validate(Supplier<Boolean> validation, String format, Object... params) {
        if (validation.get()) {
            throw new ValidationException(String.format(format, params));
        }
    }
}
