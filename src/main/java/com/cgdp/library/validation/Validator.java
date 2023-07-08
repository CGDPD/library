package com.cgdp.library.validation;

import static com.cgdp.library.validation.IsbnValidator.isValidIsbn;

import com.cgdp.library.exceptions.ValidationException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;

public class Validator {

    public static <T> T required(String paramName, T value) {
        validate(() -> value == null, "%s must not be null", paramName);
        return value;
    }

    public static String requiredNotBlank(String paramName, String value) {
        validate(() -> value == null || value.isBlank(), "%s must not be null or blank", paramName);
        return value;
    }

    public static Optional<LocalDate> checkBeforeNow(String paramName, Optional<LocalDate> value) {
        value.ifPresent(date -> validate(() -> date.isAfter(LocalDate.now()),
              "Invalid %s. %s is after the current date", paramName, date));
        return value;
    }

    public static String requiredValidIsbn(String paramName, String value) {
        validate(() -> value == null || value.isBlank() || !isValidIsbn(value),
              "%s must not be null or blank, %s is not a valid ISBN. An ISBN must have 13 numbers and have a valid check number",
              paramName, value);
        return value;
    }

    public static void validate(Supplier<Boolean> validation, String format, Object... params) {
        if (validation.get()) {
            throw new ValidationException(String.format(format, params));
        }
    }
}
