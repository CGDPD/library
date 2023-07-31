package com.cgdpd.library.mapper;

import com.cgdpd.library.dto.user.Gender;
import com.cgdpd.library.dto.user.UserDTO;
import com.cgdpd.library.entity.UserEntity;
import com.cgdpd.library.type.BookId;
import java.util.Optional;
import com.cgdpd.library.type.UserId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "mapUserId")
    UserDTO mapToUserDto(UserEntity userEntity);

    default Optional<Gender> stringToGender(String value) {
        return Optional.ofNullable(value)
              .map(String::toUpperCase)
              .map(Gender::valueOf);
    }

    @Named("mapUserId")
    default UserId mapUserId(Long id) {
        return UserId.of(id);
    }
}
