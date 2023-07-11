package com.cgdpd.library.dto.user;

import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.requiredNotBlank;

import java.util.Optional;
import lombok.Builder;

@Builder
public record CreateUserResponseDTO(Long id,
                                    String firstName,
                                    String lastName,
                                    int yearOfBirth,
                                    Optional<String> gender) {

    public CreateUserResponseDTO(Long id,
                                 String firstName,
                                 String lastName,
                                 int yearOfBirth,
                                 Optional<String> gender) {
        this.id = required("id", id);
        this.firstName = requiredNotBlank("firstName", firstName);
        this.lastName = requiredNotBlank("lastName", lastName);
        this.yearOfBirth = required("yearOfBirth", yearOfBirth);
        this.gender = gender;
    }
}
