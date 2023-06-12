package com.cgdp.library.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNValidator implements ConstraintValidator<ValidISBN, String> {

    @Override
    public void initialize(ValidISBN constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }
        String cleanedValue = value.replaceAll("[^0-9]", "");
        if (cleanedValue.length() != 13) {
            return false;
        }
        return isValidISBN(cleanedValue);
    }

    private boolean isValidISBN(String isbn) {

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            if (digit < 0 || digit > 9) {
                return false;
            }
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int lastDigit = Character.getNumericValue(isbn.charAt(12));
        if (lastDigit < 0 || lastDigit > 9) {
            return false;
        }
        return (10 - (sum % 10)) % 10 == lastDigit;
    }
}
