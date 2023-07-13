package com.cgdpd.library.mapper;

import com.cgdpd.library.dto.user.Gender;
import com.cgdpd.library.dto.user.UserDTO;
import com.cgdpd.library.entity.UserEntity;
import java.util.Optional;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO mapToUserDto(UserEntity userEntity);

    default Optional<Gender> stringToGender(String value) {
        return Optional.ofNullable(value)
              .map(String::toUpperCase)
              .map(Gender::valueOf);
    }
}
