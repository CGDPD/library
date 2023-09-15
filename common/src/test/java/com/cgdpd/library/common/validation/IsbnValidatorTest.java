package com.cgdpd.library.common.validation;

import static com.cgdpd.library.common.validation.IsbnValidator.isValidIsbn13;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class IsbnValidatorTest {

    @Test
    void should_return_true_when_isbn_is_valid() {
        // given
        var validIsbn = "9780134685991";

        // when
        var isValid = isValidIsbn13(validIsbn);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    void should_return_false_when_isbn_is_invalid() {
        // given
        var invalidIsbn = "9780134685992";

        // when
        var isValid = isValidIsbn13(invalidIsbn);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    void should_return_false_when_isbn_has_non_numeric_characters() {
        // given
        var invalidIsbn = "9780a3468b5991";

        // when
        var isValid = isValidIsbn13(invalidIsbn);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    void should_return_false_when_isbn_has_incorrect_length() {
        // given
        var invalidIsbn = "97801346859";

        // when
        var isValid = isValidIsbn13(invalidIsbn);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    void should_return_false_when_isbn_has_invalid_check_digit() {
        // given
        var invalidIsbn = "9780134685999";

        // when
        var isValid = isValidIsbn13(invalidIsbn);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    void should_return_false_when_isbn_has_invalid_prefix() {
        // given
        var invalidIsbn = "1234567890123";

        // when
        var isValid = isValidIsbn13(invalidIsbn);

        // then
        assertThat(isValid).isFalse();
    }
}
