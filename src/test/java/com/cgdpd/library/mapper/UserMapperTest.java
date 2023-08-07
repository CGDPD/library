package com.cgdpd.library.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.cgdpd.library.dto.user.Gender;
import com.cgdpd.library.dto.user.UserDTO;
import com.cgdpd.library.entity.UserEntity;
import org.junit.jupiter.api.Test;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapperImpl();

    @Test
    void should_map_to_user_dto() {
        // given
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setYearOfBirth((short) 1999);
        userEntity.setGender("MALE");

        // when
        UserDTO userDTO = userMapper.mapToUserDto(userEntity);

        // then
        assertThat(userDTO.id().value()).isEqualTo(userEntity.getId());
        assertThat(userDTO.firstName()).isEqualTo(userEntity.getFirstName());
        assertThat(userDTO.lastName()).isEqualTo(userEntity.getLastName());
        assertThat(userDTO.yearOfBirth()).isEqualTo(userEntity.getYearOfBirth());
        assertThat(userDTO.gender()).hasValue(Gender.valueOf(userEntity.getGender()));
    }
}
