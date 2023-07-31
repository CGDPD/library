package com.cgdpd.library;

import static com.cgdpd.library.dto.user.Gender.MALE;

import com.cgdpd.library.dto.user.UserDTO;
import com.cgdpd.library.entity.UserEntity;
import com.cgdpd.library.type.UserId;
import java.util.Optional;

public class UserTestData {

    public static UserDTO.UserDTOBuilder aUser() {
        return UserDTO.builder()
              .id(UserId.of(1L))
              .firstName("John")
              .lastName("Doe")
              .yearOfBirth((short) 1996)
              .gender(Optional.of(MALE));
    }

    public static UserEntity.UserEntityBuilder aUserEntity() {
        return UserEntity.builder()
              .id(1L)
              .firstName("John")
              .lastName("Doe")
              .yearOfBirth((short) 1996)
              .gender(MALE.name());
    }
}
