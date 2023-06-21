package com.cgdp.library.validation;

import static com.cgdp.library.validation.ValidIsbn.isValidISBN;

import com.cgdp.library.exceptions.ValidationException;
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

    public static LocalDate requiredValidDate(String paramName, LocalDate value) {

        validate(() -> value.isAfter(LocalDate.now()), "Invalid %s", paramName);
        return value;
    }

    public static String requiredValidIsbn(String paramName, String value) {

        validate(
              () -> value == null || value.isBlank() || !isValidISBN(value),
              "%s is not a valid ISBN", paramName);
        return value;
    }

    public static void validate(Supplier<Boolean> validation, String format, Object... params) {
        if (validation.get()) {
            throw new ValidationException(String.format(format, params));
        }
    }
}
