package com.cgdpd.library.validation;

import com.cgdpd.library.entity.TrackingStatus;
import com.cgdpd.library.exceptions.ValidationException;
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

    public static Optional<Short> checkYearNotFuture(String paramName, Optional<Short> value) {
        value.ifPresent(year -> {
            int currentYear = LocalDate.now().getYear();
            validate(() -> year > currentYear,
                  "Invalid %s. %s is after the current year", paramName, year);
        });
        return value;
    }

    public static String requiredValidIsbn(String paramName, String value) {
        validate(() -> value == null || value.isBlank() || !IsbnValidator.isValidIsbn(value),
              "%s must not be null or blank, %s is not a valid ISBN. An ISBN must have 13 numbers and have a valid check number",
              paramName, value);
        return value;
    }

    public static Optional<Long> checkTrackingStatus(String paramName,
                                                     TrackingStatus trackingStatus,
                                                     Optional<Long> value) {
        switch (trackingStatus) {
            case ON_HOLD:
            case CHECKED_OUT:
            case LOST:
                validate(() -> value.isEmpty(), "%s must be empty for trackingStatus %s", paramName,
                      trackingStatus);
                break;
            case AVAILABLE:
            case RETIRED:
            case REFERENCE:
                validate(() -> value.isPresent(), "%s must be present for trackingStatus %s",
                      paramName, trackingStatus);
                break;
        }
        return value;
    }

    public static void validate(Supplier<Boolean> validation, String format, Object... params) {
        if (validation.get()) {
            throw new ValidationException(String.format(format, params));
        }
    }
}
