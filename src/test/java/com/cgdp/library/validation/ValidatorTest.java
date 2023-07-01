package com.cgdp.library.validation;

import com.cgdp.library.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

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
    void should_throw_exception_when_value_is_not_null() {
        // given
        String paramName = "value";
        String value = "valid";

        // when
        Throwable thrownException = catchThrowable(() -> Validator.required(paramName, value));

        // then
        assertThat(thrownException).isNull();
    }

    @Test
    void should_throw_exception_when_value_is_empty() {
        //given
        String paramName = "value";
        String value = "";

        //when
        Throwable thrownException = catchThrowable(() -> Validator.requiredNotBlank(paramName, value));

        //then
        assertThat(thrownException)
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("%s must not be null or blank", paramName));
    }

    @Test
    void should_not_throw_exception_if_value_is_not_empty() {
        // given
        String paramName = "value";
        String value = "valid";

        // when
        Throwable thrownException = catchThrowable(() -> Validator.requiredNotBlank(paramName, value));

        // then
        assertThat(thrownException).isNull();
    }

    @Test
    void should_throw_exception_when_the_value_is_null() {
        //given
        String paramName = "value";
        String value = null;

        //when
        Throwable thrownException = catchThrowable(() -> Validator.requiredNotBlank(paramName, value));

        //then
        assertThat(thrownException)
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("%s must not be null or blank", paramName));
    }

    @Test
    void should_throw_exception_when_date_value_is_after_now() {
        //given
        String paramName = "value";
        LocalDate value = LocalDate.now().plusDays(1);

        //when
        Throwable thrownException = catchThrowable(() -> Validator.requiredBeforeNow(paramName, value));

        //then
        assertThat(thrownException)
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("Invalid %s. %s is after current date", paramName, value));
    }

    @Test
    void should_not_throw_exception_if_date_value_is_before_now() {
        // given
        String paramName = "value";
        LocalDate value = LocalDate.now().minusDays(1);

        // when
        Throwable thrownException = catchThrowable(() -> Validator.requiredBeforeNow(paramName, value));

        // then
        assertThat(thrownException).isNull();
    }

    @Test
    void should_throw_exception_when_isbn_value_is_invalid() {
        //given
        String paramName = "value";
        String value = "9780134685992";

        //when
        Throwable thrownException = catchThrowable(() -> Validator.requiredValidIsbn(paramName, value));

        //then
        assertThat(thrownException)
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("%s is not a valid ISBN. An ISBN must have 13 numbers and have a valid check number",
                    value));
    }

    @Test
    void should_throw_exception_when_isbn_value_is_empty() {
        //given
        String paramName = "value";
        String value = "";

        //when
        Throwable thrownException = catchThrowable(() -> Validator.requiredValidIsbn(paramName, value));

        //then
        assertThat(thrownException)
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("%s is not a valid ISBN. An ISBN must have 13 numbers and have a valid check number",
                    value));
    }

    @Test
    void should_not_throw_exception_if_isbn_value_is_valid() {
        // given
        String paramName = "value";
        String value = "9780134685991";

        // when
        Throwable thrownException = catchThrowable(() -> Validator.requiredValidIsbn(paramName, value));

        // then
        assertThat(thrownException).isNull();
    }

    @Test
    void should_not_throw_exception_when_isbn_value_is_not_empty() {
        //given
        String paramName = "value";
        String value = "9780134685991";

        //when
        Throwable thrownException = catchThrowable(() -> Validator.requiredValidIsbn(paramName, value));

        //then
        assertThat(thrownException).isNull();
    }
}
