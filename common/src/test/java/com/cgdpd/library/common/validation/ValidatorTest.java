package com.cgdpd.library.common.validation;

import static com.cgdpd.library.common.validation.Validator.checkYearNotFuture;
import static com.cgdpd.library.common.validation.Validator.required;
import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;
import static com.cgdpd.library.common.validation.Validator.requiredNotEmpty;
import static com.cgdpd.library.common.validation.Validator.requiredNotNegative;
import static com.cgdpd.library.common.validation.Validator.requiredPositive;
import static com.cgdpd.library.common.validation.Validator.requiredValidHttpStatus;
import static com.cgdpd.library.common.validation.Validator.requiredValidIsbn13;
import static com.cgdpd.library.common.validation.Validator.requiredValidUrl;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.cgdpd.library.common.exception.ValidationException;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ValidatorTest {

    @Test
    void should_throw_exception_when_value_is_null() {
        // given
        var paramName = "value";
        String value = null;

        // when
        var thrownException = assertThatThrownBy(() -> required(paramName, value));

        // then
        thrownException
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("%s must not be null", paramName));
    }

    @Test
    void should_return_value_when_value_is_not_null() {
        // given
        var paramName = "value";
        var value = "valid";

        // when
        var result = required(paramName, value);

        // then
        assertThat(result).isEqualTo(value);
    }

    @Test
    void should_throw_exception_when_value_is_blank() {
        // given
        var paramName = "value";
        var value = "";

        // when
        var thrownException = assertThatThrownBy(() -> requiredNotBlank(paramName, value));

        // then
        thrownException
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("%s must not be null or blank", paramName));
    }

    @Test
    void should_return_value_if_value_is_not_blank() {
        // given
        var paramName = "value";
        var value = "valid";

        // when
        var result = requiredNotBlank(paramName, value);

        // then
        assertThat(result).isEqualTo(value);
    }

    @Test
    void should_throw_exception_when_the_value_is_null() {
        // given
        var paramName = "value";
        String value = null;

        // when
        var thrownException = assertThatThrownBy(() -> requiredNotBlank(paramName, value));

        // then
        thrownException
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("%s must not be null or blank", paramName));
    }

    @Test
    void should_throw_exception_when_collection_is_null() {
        // given
        var paramName = "values";
        List<String> values = null;

        // when
        var thrownException = assertThatThrownBy(() -> requiredNotEmpty(paramName, values));

        // then
        thrownException
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("Collection %s must not be null or empty", paramName));
    }

    @Test
    void should_throw_exception_when_collection_is_empty() {
        // given
        var paramName = "values";
        List<String> values = emptyList();

        // when
        var thrownException = assertThatThrownBy(() -> requiredNotEmpty(paramName, values));

        // then
        thrownException
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("Collection %s must not be null or empty", paramName));
    }

    @Test
    void should_return_collection_when_collection_is_not_empty() {
        // given
        var paramName = "values";
        var values = List.of("value1", "value2");

        // when
        var result = requiredNotEmpty(paramName, values);

        // then
        assertThat(result).isEqualTo(values);
    }

    @Test
    void should_throw_exception_when_required_positive_number_is_null() {
        // given
        var paramName = "value";

        // when
        var thrownException = assertThatThrownBy(() -> requiredPositive(paramName, null));

        // then
        thrownException
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format(
                    "%s must not be null, must be a positive number",
                    paramName));
    }

    @Test
    void should_return_value_when_value_is_positive() {
        // given
        var paramName = "value";
        var value = 1;

        // when
        var result = requiredPositive(paramName, value);

        // then
        assertThat(result).isEqualTo(value);
    }

    @Test
    void should_throw_exception_when_required_not_negative_number_is_null() {
        // given
        var paramName = "value";

        // when
        var thrownException = assertThatThrownBy(() -> requiredNotNegative(paramName, null));

        // then
        thrownException
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format(
                    "%s must not be null or negative",
                    paramName));
    }

    @Test
    void should_return_value_when_value_is_not_negative() {
        // given
        var paramName = "value";
        var value = 0;

        // when
        var result = requiredNotNegative(paramName, value);

        // then
        assertThat(result).isEqualTo(value);
    }

    @Test
    void should_throw_exception_when_year_value_is_after_now() {
        // given
        var paramName = "value";
        var value = Optional.of((short) (LocalDate.now().plusYears(1).getYear()));

        // when
        var thrownException = assertThatThrownBy(() -> checkYearNotFuture(paramName, value));

        // then
        thrownException
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("Invalid %s. %s is after the current year", paramName,
                    value.orElseThrow()));
    }

    @Test
    void should_return_year_value_when_it_is_not_future_year() {
        // given
        var paramName = "value";
        var value = Optional.of((short) (LocalDate.now().getYear() - 1));

        // when
        var result = checkYearNotFuture(paramName, value);

        // then
        assertThat(result).isEqualTo(value);
    }

    @Test
    void should_throw_exception_when_isbn_value_is_invalid() {
        // given
        var paramName = "value";
        var value = "9780134685992";

        // when
        var thrownException = assertThatThrownBy(() -> requiredValidIsbn13(paramName, value));

        // then
        thrownException
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format(
                    "%s must not be null or blank, %s is not a valid ISBN. An ISBN must have 13 numbers and have a valid check number",
                    paramName, value));
    }

    @Test
    void should_throw_exception_when_isbn_value_is_blank() {
        // given
        var paramName = "value";
        var value = "";

        // when
        var thrownException = assertThatThrownBy(() -> requiredValidIsbn13(paramName, value));

        // then
        thrownException
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format(
                    "%s must not be null or blank, %s is not a valid ISBN. An ISBN must have 13 numbers and have a valid check number",
                    paramName, value));
    }

    @Test
    void should_return_value_when_isbn_is_valid() {
        // given
        var paramName = "value";
        var value = "9780134685991";

        // when
        var result = requiredValidIsbn13(paramName, value);

        // then
        assertThat(result).isEqualTo(value);
    }

    @Test
    void should_throw_exception_when_url_is_null() {
        // given
        String value = null;

        // when
        var thrownException = assertThatThrownBy(() -> requiredValidUrl(value));

        // then
        thrownException
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("%s is not a valid URL", value));
    }

    @Test
    void should_throw_exception_when_url_is_blank() {
        // given
        var value = "";

        // when
        var thrownException = assertThatThrownBy(() -> requiredValidUrl(value));

        // then
        thrownException
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("%s is not a valid URL", value));
    }

    @Test
    void should_throw_exception_when_url_is_malformed() {
        // given
        var value = "invalidURL";

        // when
        var thrownException = assertThatThrownBy(() -> requiredValidUrl(value));

        // then
        thrownException
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("%s is not a valid URL", value));
    }

    @Test
    void should_return_url_when_it_is_valid() {
        // given
        var value = "https://www.example.com";

        // when
        var result = requiredValidUrl(value);

        // then
        assertThat(result).isEqualTo(value);
    }

    @Test
    void should_throw_exception_when_http_status_code_is_not_valid() {
        // given
        var value = 99;

        // when
        var thrownException = assertThatThrownBy(() -> requiredValidHttpStatus(value));

        // then
        thrownException
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("%s is not a valid http status", value));
    }

    @Test
    void should_throw_exception_when_http_status_code_is_null() {
        // given
        Integer value = null;

        // when
        var thrownException = assertThatThrownBy(() -> requiredValidHttpStatus(value));

        // then
        thrownException
              .isInstanceOf(ValidationException.class)
              .hasMessage(String.format("%s is not a valid http status", value));
    }

    @Test
    void should_return_value_when_http_status_code_is_valid() {
        // given
        var value = 200;

        // when
        var result = requiredValidHttpStatus(value);

        // then
        assertThat(result).isEqualTo(value);
    }
}
