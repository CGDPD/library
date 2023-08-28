package com.cgdpd.library.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.cgdpd.library.entity.UserEntity;
import com.cgdpd.library.model.user.Gender;
import org.junit.jupiter.api.Test;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapperImpl();

    @Test
    void should_map_user_entity_to_user() {
        // given
        var userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setYearOfBirth((short) 1999);
        userEntity.setGender("MALE");

        // when
        var user = userMapper.mapToUser(userEntity);

        // then
        assertThat(user.id().value()).isEqualTo(userEntity.getId());
        assertThat(user.firstName()).isEqualTo(userEntity.getFirstName());
        assertThat(user.lastName()).isEqualTo(userEntity.getLastName());
        assertThat(user.yearOfBirth()).isEqualTo(userEntity.getYearOfBirth());
        assertThat(user.gender()).hasValue(Gender.valueOf(userEntity.getGender()));
    }
}
