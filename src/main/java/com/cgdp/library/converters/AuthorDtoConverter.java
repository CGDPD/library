package com.cgdp.library.converters;

import com.cgdp.library.dto.CreateAuthorRequestDTO;
import com.cgdp.library.entity.AuthorEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AuthorDtoConverter {

    private final ModelMapper modelMapper;

    public CreateAuthorRequestDTO toDTO(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, CreateAuthorRequestDTO.class);
    }

    public AuthorEntity toEntity(CreateAuthorRequestDTO createAuthorRequestDTO) {
        return modelMapper.map(createAuthorRequestDTO, AuthorEntity.class);
    }
}
