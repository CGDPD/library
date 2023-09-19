package com.cgdpd.library.common.type;

import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;

public record Password(String value) {

    public Password {
        requiredNotBlank("value", value);
    }

    public static Password of(String value) {
        return new Password(value);
    }

    @Override
    public String toString() {
        return "<masked>";
    }
}
