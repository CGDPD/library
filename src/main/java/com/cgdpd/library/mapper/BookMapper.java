package com.cgdpd.library.mapper;

import com.cgdpd.library.dto.book.BookDTO;
import com.cgdpd.library.dto.book.CreateBookRequestDTO;
import com.cgdpd.library.entity.BookEntity;
import java.util.Optional;
import com.cgdpd.library.type.AuthorId;
import com.cgdpd.library.type.BookId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "authorId", target = "authorEntity.id", qualifiedByName = "mapFromAuthorId")
    @Mapping(source = "publicationYear", target = "publicationYear", qualifiedByName = "mapOptionalToShort")
    BookEntity mapToBookEntity(CreateBookRequestDTO requestDTO);

    @Mapping(source = "authorEntity.id", target = "authorId", qualifiedByName = "mapAuthorId")
    @Mapping(source = "publicationYear", target = "publicationYear", qualifiedByName = "mapShortToOptional")
    @Mapping(source = "id", target = "id", qualifiedByName = "mapBookId")
    BookDTO mapToBookDTO(BookEntity bookEntity);

    @Named("mapOptionalToShort")
    default Short mapOptionalToShort(Optional<Short> value) {
        return value.orElse(null);
    }

    @Named("mapShortToOptional")
    default Optional<Short> mapShortToOptional(Short value) {
        return Optional.ofNullable(value);
    }

    @Named("mapBookId")
    default BookId mapBookId(Long id) {
        return BookId.of(id);
    }

    @Named("mapAuthorId")
    default AuthorId mapAuthorId(Long id) {
        return AuthorId.of(id);
    }

    @Named("mapFromAuthorId")
    default Long mapAuthorId(AuthorId id) {
        return id.value();
    }
}
