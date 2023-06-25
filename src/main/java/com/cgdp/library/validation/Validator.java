package com.cgdp.library.validation;

import static com.cgdp.library.validation.IsbnValidator.isNullOrBlank;
import static com.cgdp.library.validation.IsbnValidator.isValidIsbn;

import com.cgdp.library.exceptions.ValidationException;
import java.time.LocalDate;
import java.util.function.Supplier;

public class Validator {

    public static <T> T required(String paramName, T value) {
        validate(() -> value == null, "%s must not be null", paramName);
        return value;
    }

    public static String requiredNotNullOrBlank(String paramName, String value) {
        validate(() -> isNullOrBlank(value), "%s must not be null or blank", paramName);
        return value;
    }

    public static LocalDate requiredBeforeNow(String paramName, LocalDate value) {
        if (value != null) {
            validate(() -> value.isAfter(LocalDate.now()),
                  "Invalid %s. %s is after current date", paramName, value);
        }
        return value;
    }

    public static String requiredValidIsbn(String paramName, String value) {
        validate(() -> value.isBlank() || !isValidIsbn(value),
              "%s is not a valid ISBN. An ISBN must have 13 numbers and have a valid check number",
              value);
        return value;
    }

    public static void validate(Supplier<Boolean> validation, String format, Object... params) {
        if (validation.get()) {
            throw new ValidationException(String.format(format, params));
        }
    }
}
