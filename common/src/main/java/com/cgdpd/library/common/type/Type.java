package com.cgdpd.library.common.type;

import static com.cgdpd.library.common.validation.Validator.required;

import java.util.Objects;

public abstract class Type<T> {

    private final T value;

    protected Type(T value) {
        this.value = required("value", value);
    }

    public T value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var type = (Type<?>) o;
        return Objects.equals(value, type.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
