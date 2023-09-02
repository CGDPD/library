package com.cgdpd.library.types;


import static com.cgdpd.library.common.validation.Validator.required;

import java.util.Objects;

public abstract class IdType {

    private final Long id;


    protected IdType(Long id) {
        this.id = required("id", id);
    }

    public Long value() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var idType = (IdType) o;
        return Objects.equals(id, idType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "IdType{" +
              "id=" + id +
              '}';
    }
}
