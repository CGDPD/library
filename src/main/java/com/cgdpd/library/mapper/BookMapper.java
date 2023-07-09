package com.cgdpd.library.mapper;

import com.cgdpd.library.dto.book.BookDTO;
import com.cgdpd.library.dto.book.CreateBookRequestDTO;
import com.cgdpd.library.entity.BookEntity;
import java.time.LocalDate;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "authorId", target = "authorEntity.id")
    @Mapping(source = "publicationYear", target = "publicationYear", qualifiedByName = "mapOptionalToLocalDate")
    BookEntity mapToBookEntity(CreateBookRequestDTO requestDTO);

    @Mapping(source = "authorEntity.id", target = "authorId")
    @Mapping(source = "publicationYear", target = "publicationYear", qualifiedByName = "mapLocalDateToOptional")
    BookDTO mapToBookDTO(BookEntity bookEntity);

    @Named("mapOptionalToLocalDate")
    default LocalDate mapOptionalToLocalDate(Optional<LocalDate> value) {
        return value.orElse(null);
    }

    @Named("mapLocalDateToOptional")
    default Optional<LocalDate> mapLocalDateToOptional(LocalDate value) {
        return Optional.ofNullable(value);
    }
}
