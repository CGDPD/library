package com.cgdpd.library.service;

import com.cgdpd.library.dto.user.CreateUserRequestDTO;
import com.cgdpd.library.dto.user.UserDTO;
import com.cgdpd.library.entity.UserEntity;
import com.cgdpd.library.mapper.UserMapper;
import com.cgdpd.library.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO createUser(CreateUserRequestDTO requestDTO) {
        UserEntity userEntity = userMapper.mapToUserEntity(requestDTO);
        UserEntity savedUser = userRepository.save(userEntity);
        return userMapper.mapToUserDto(savedUser);
    }
}
