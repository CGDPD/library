package com.cgdp.library.validation;

import static com.cgdp.library.validation.IsbnValidator.isValidIsbn;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IsbnValidatorTest {

    @Test
    void should_return_valid_isbn() {
        String validIsbn = "9780134685991";
        assertTrue(isValidIsbn(validIsbn));
    }

    @Test
    void should_return_invalid_isbn() {
        String invalidIsbn = "9780134685992";
        assertFalse(isValidIsbn(invalidIsbn));
    }

    @Test
    void should_return_invalid_isbn_with_non_numeric_characters() {
        String invalidIsbn = "9780a3468b5991";
        assertFalse(isValidIsbn(invalidIsbn));
    }

    @Test
    void should_return_invalid_isbn_with_incorrect_length() {
        String invalidIsbn = "97801346859";
        assertFalse(isValidIsbn(invalidIsbn));
    }

    @Test
    void should_return_invalid_isbn_with_invalid_check_digit() {
        String invalidIsbn = "9780134685999";
        assertFalse(isValidIsbn(invalidIsbn));
    }
}
