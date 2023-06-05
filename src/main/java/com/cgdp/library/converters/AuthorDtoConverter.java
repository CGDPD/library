package com.cgdp.library.converters;

import com.cgdp.library.dto.AuthorDTO;
import com.cgdp.library.entity.AuthorEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AuthorDtoConverter {

    private ModelMapper modelMapper;
    public AuthorDTO toDTO(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDTO.class);
    }

    public AuthorEntity toEntity(AuthorDTO authorDTO) {
        return modelMapper.map(authorDTO, AuthorEntity.class);
    }
}
