package com.cgdpd.library.validation;

import static com.cgdpd.library.validation.IsbnValidator.isValidIsbn;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class IsbnValidatorTest {

    @Test
    void should_return_true_when_isbn_is_valid() {
        // given
        String validIsbn = "9780134685991";

        // when
        boolean isValid = isValidIsbn(validIsbn);

        //then
        assertThat(isValid).isTrue();
    }

    @Test
    void should_return_false_when_isbn_is_invalid() {
        // given
        String invalidIsbn = "9780134685992";

        //when
        boolean isValid = isValidIsbn(invalidIsbn);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    void should_return_false_when_isbn_has_non_numeric_characters() {
        // given
        String invalidIsbn = "9780a3468b5991";

        //when
        boolean isValid = isValidIsbn(invalidIsbn);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    void should_return_false_when_isbn_has_incorrect_length() {
        // given
        String invalidIsbn = "97801346859";

        //when
        boolean isValid = isValidIsbn(invalidIsbn);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    void should_return_false_when_isbn_has_invalid_check_digit() {
        // given
        String invalidIsbn = "9780134685999";

        //when
        boolean isValid = isValidIsbn(invalidIsbn);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    void should_return_false_when_isbn_has_invalid_prefix() {
        // given
        String invalidIsbn = "1234567890123";

        //when
        boolean isValid = isValidIsbn(invalidIsbn);

        // then
        assertThat(isValid).isFalse();
    }
}
