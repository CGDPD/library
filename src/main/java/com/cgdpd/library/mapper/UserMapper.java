package com.cgdpd.library.mapper;

import com.cgdpd.library.dto.user.CreateUserRequestDTO;
import com.cgdpd.library.dto.user.UserDTO;
import com.cgdpd.library.entity.UserEntity;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "gender", target = "gender", qualifiedByName = "stringToOptional")
    UserEntity mapToUserEntity(CreateUserRequestDTO requestDTO);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "gender", target = "gender", qualifiedByName = "optionalToString")
    UserDTO mapToUserDto(UserEntity userEntity);

    default Optional<String> stringToOptional(String value) {
        return Optional.ofNullable(value);
    }

    default String optionalToString(Optional<String> optional) {
        return optional.orElse(null);
    }
}
