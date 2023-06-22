package com.cgdp.library.validation;

import static com.cgdp.library.validation.IsbnValidator.isValidIsbn;

import com.cgdp.library.exceptions.ValidationException;
import java.time.LocalDate;
import java.util.function.Supplier;

public class Validator {

    public static Long required(String paramName, Long value) {
        validate(() -> value == null, "%s must not be null", paramName);
        return value;
    }

    public static String requiredNotBlank(String paramName, String value) {
        validate(() -> value.isBlank(), "%s must not be blank", paramName);
        return value;
    }

    public static LocalDate requiredPastDate(String paramName, LocalDate value) {
        if (value != null) {
            validate(() -> value.isAfter(LocalDate.now()),
                  "Invalid %s. %s is greater than the current date", paramName, value);
        }
        return value;
    }

    public static String requiredValidIsbn(String paramName, String value) {
        validate(() -> value.isBlank() || !isValidIsbn(value),
              "%s is not a valid ISBN. An ISBN must have 13 numbers and have a valid check number",  value);
        return value;
    }

    public static void validate(Supplier<Boolean> validation, String format, Object... params) {
        if (validation.get()) {
            throw new ValidationException(String.format(format, params));
        }
    }
}
