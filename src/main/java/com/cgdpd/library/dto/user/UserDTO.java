package com.cgdpd.library.dto.user;

import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.requiredNotBlank;

import java.util.Optional;
import com.cgdpd.library.type.UserId;
import lombok.Builder;

@Builder
public record UserDTO(UserId id,
                      String firstName,
                      String lastName,
                      short yearOfBirth,
                      Optional<Gender> gender) {

    public UserDTO(UserId id,
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
