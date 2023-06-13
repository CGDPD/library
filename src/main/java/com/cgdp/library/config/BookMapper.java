package com.cgdp.library.config;

import com.cgdp.library.dto.book.BookDTO;
import com.cgdp.library.dto.book.CreateBookRequestDTO;
import com.cgdp.library.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "authorId", target = "authorEntity.id")
    BookEntity mapToBookEntity(CreateBookRequestDTO requestDTO);

    BookDTO mapToBookDTO(BookEntity bookEntity);
}
