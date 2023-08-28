package com.cgdpd.library.model.user;

import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.requiredNotBlank;

import com.cgdpd.library.type.UserId;
import java.util.Optional;
import lombok.Builder;

@Builder
public record User(UserId id,
                   String firstName,
                   String lastName,
                   short yearOfBirth,
                   Optional<Gender> gender) {

    public User(UserId id,
                String firstName,
                String lastName,
                short yearOfBirth,
                Optional<Gender> gender) {
        this.id = required("id", id);
        this.firstName = requiredNotBlank("firstName", firstName);
        this.lastName = requiredNotBlank("lastName", lastName);
        this.yearOfBirth = required("yearOfBirth", yearOfBirth);
        this.gender = gender;
    }
}
