package com.cgdpd.library.mapper;

import com.cgdpd.library.entity.UserEntity;
import com.cgdpd.library.model.user.Gender;
import com.cgdpd.library.model.user.User;
import com.cgdpd.library.type.UserId;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "mapUserId")
    User mapToUser(UserEntity userEntity);

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
