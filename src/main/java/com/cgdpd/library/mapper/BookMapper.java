package com.cgdpd.library.mapper;

import com.cgdpd.library.dto.book.BookDTO;
import com.cgdpd.library.dto.book.CreateBookRequestDTO;
import com.cgdpd.library.entity.BookEntity;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "authorId", target = "authorEntity.id")
    @Mapping(source = "publicationYear", target = "publicationYear", qualifiedByName = "mapOptionalToShort")
    BookEntity mapToBookEntity(CreateBookRequestDTO requestDTO);

    @Mapping(source = "authorEntity.id", target = "authorId")
    @Mapping(source = "publicationYear", target = "publicationYear", qualifiedByName = "mapShortToOptional")
    BookDTO mapToBookDTO(BookEntity bookEntity);

    @Named("mapOptionalToShort")
    default Short mapOptionalToShort(Optional<Short> value) {
        return value.orElse(null);
    }

    @Named("mapShortToOptional")
    default Optional<Short> mapShortToOptional(Short value) {
        return Optional.ofNullable(value);
    }
}
