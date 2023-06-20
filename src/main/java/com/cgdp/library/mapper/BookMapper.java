package com.cgdp.library.mapper;

import com.cgdp.library.dto.book.BookDTO;
import com.cgdp.library.dto.book.CreateBookRequestDTO;
import com.cgdp.library.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "authorId", target = "authorEntity.id")
    BookEntity mapToBookEntity(CreateBookRequestDTO requestDTO);

    @Mapping(source = "authorEntity.id", target = "authorId")
    BookDTO mapToBookDTO(BookEntity bookEntity);
}
