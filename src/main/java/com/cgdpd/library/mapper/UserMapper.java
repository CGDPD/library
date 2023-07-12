package com.cgdpd.library.mapper;

import com.cgdpd.library.Gender;
import com.cgdpd.library.dto.user.UserDTO;
import com.cgdpd.library.entity.UserEntity;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "firstName", target = "firstName")
    UserDTO mapToUserDto(UserEntity userEntity);

    default Optional<Gender> map(String value) {
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(Gender.valueOf(value.toUpperCase()));
    }
}
