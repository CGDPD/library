package com.cgdpd.library;

import static com.cgdpd.library.model.user.Gender.FEMALE;
import static com.cgdpd.library.model.user.Gender.MALE;

import com.cgdpd.library.entity.UserEntity;
import com.cgdpd.library.model.user.User;
import com.cgdpd.library.type.UserId;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class UserTestData {

    private static final AtomicLong userId = new AtomicLong(1L);
    public static final UserEntity USER_LOLA_ROGER = aUserEntity(userId.getAndIncrement())
          .firstName("Lola")
          .lastName("Roger")
          .gender(FEMALE.name())
          .build();

    public static final UserEntity USER_LOUIS_CASTRO = aUserEntity(userId.getAndIncrement())
          .firstName("Louis")
          .lastName("Castro")
          .gender(MALE.name())
          .build();


    public static User.UserBuilder aUser() {
        return User.builder()
              .id(UserId.of(1L))
              .firstName("John")
              .lastName("Doe")
              .yearOfBirth((short) 1996)
              .gender(Optional.of(MALE));
    }

    public static UserEntity.UserEntityBuilder aUserEntity() {
        return aUserEntity(1L);
    }

    public static UserEntity.UserEntityBuilder aUserEntity(Long id) {
        return UserEntity.builder()
              .id(id)
              .firstName("John")
              .lastName("Doe")
              .yearOfBirth((short) 1996)
              .gender(MALE.name());
    }
}
