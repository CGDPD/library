package com.cgdpd.library.dto.user;

import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.requiredNotBlank;

import java.util.Optional;
import lombok.Builder;

@Builder
public record CreateUserRequestDTO(String firstName,
                                   String lastName,
                                   int yearOfBirth,
                                   Optional<String> gender) {

    public CreateUserRequestDTO(String firstName,
                                String lastName,
                                int yearOfBirth,
                                Optional<String> gender) {
        this.firstName = requiredNotBlank("firstName", firstName);
        this.lastName = requiredNotBlank("lastName", lastName);
        this.yearOfBirth = required("yearOfBirth", yearOfBirth);
        this.gender = gender;
    }
}
