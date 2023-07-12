package com.cgdpd.library.validation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

import com.cgdpd.library.exceptions.ValidationException;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class ValidatorTest {

    @Test
    void should_throw_exception_when_value_is_null() {
        // given
        String paramName = "value";
        String value = null;

        // when
        Throwable thrownException = catchThrowable(() -> Validator.required(paramName, value));

        // then
        assertThat(thrownException)
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("%s must not be null", paramName));
    }

    @Test
    void should_return_value_when_value_is_not_null() {
        // given
        String paramName = "value";
        String value = "valid";

        // when
        String result = Validator.required(paramName, value);

        // then
        assertThat(result).isEqualTo(value);
    }

    @Test
    void should_throw_exception_when_value_is_blank() {
        // given
        String paramName = "value";
        String value = "";

        // when
        Throwable thrownException = catchThrowable(
              () -> Validator.requiredNotBlank(paramName, value));

        // then
        assertThat(thrownException)
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("%s must not be null or blank", paramName));
    }

    @Test
    void should_return_value_if_value_is_not_blank() {
        // given
        String paramName = "value";
        String value = "valid";

        // when
        String result = Validator.requiredNotBlank(paramName, value);

        // then
        assertThat(result).isEqualTo(value);
    }

    @Test
    void should_throw_exception_when_the_value_is_null() {
        // given
        String paramName = "value";
        String value = null;

        // when
        Throwable thrownException = catchThrowable(
              () -> Validator.requiredNotBlank(paramName, value));

        // then
        assertThat(thrownException)
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("%s must not be null or blank", paramName));
    }

    @Test
    void should_throw_exception_when_year_value_is_after_now() {
        // given
        String paramName = "value";
        Optional<Short> value = Optional.of((short) (LocalDate.now().plusYears(1).getYear()));

        // when
        Throwable thrownException = catchThrowable(
              () -> Validator.checkYearNotFuture(paramName, value));

        // then
        assertThat(thrownException)
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("Invalid %s. %s is after the current year", paramName,
                    value.orElseThrow()));
    }

    @Test
    void should_return_year_value_when_it_is_not_future_year() {
        // given
        String paramName = "value";
        Optional<Short> value = Optional.of((short) (LocalDate.now().getYear() - 1));

        // when
        Optional<Short> result = Validator.checkYearNotFuture(paramName, value);

        // then
        assertThat(result).isEqualTo(value);
    }

    @Test
    void should_throw_exception_when_isbn_value_is_invalid() {
        // given
        String paramName = "value";
        String value = "9780134685992";

        // when
        Throwable thrownException = catchThrowable(
              () -> Validator.requiredValidIsbn(paramName, value));

        // then
        assertThat(thrownException)
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format(
                    "%s must not be null or blank, %s is not a valid ISBN. An ISBN must have 13 numbers and have a valid check number",
                    paramName, value));
    }

    @Test
    void should_throw_exception_when_isbn_value_is_blank() {
        // given
        String paramName = "value";
        String value = "";

        // when
        Throwable thrownException = catchThrowable(
              () -> Validator.requiredValidIsbn(paramName, value));

        // then
        assertThat(thrownException)
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format(
                    "%s must not be null or blank, %s is not a valid ISBN. An ISBN must have 13 numbers and have a valid check number",
                    paramName, value));
    }

    @Test
    void should_return_value_when_isbn_is_valid() {
        // given
        String paramName = "value";
        String value = "9780134685991";

        // when
        String result = Validator.requiredValidIsbn(paramName, value);

        // then
        assertThat(result).isEqualTo(value);
    }
}
