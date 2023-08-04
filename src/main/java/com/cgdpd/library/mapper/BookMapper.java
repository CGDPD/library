package com.cgdpd.library.mapper;

import com.cgdpd.library.dto.book.BookDTO;
import com.cgdpd.library.dto.book.CreateBookRequestDTO;
import com.cgdpd.library.entity.BookEntity;
import com.cgdpd.library.type.AuthorId;
import com.cgdpd.library.type.BookId;
import com.cgdpd.library.type.Isbn13;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "authorId", target = "authorEntity.id", qualifiedByName = "mapFromAuthorId")
    BookEntity mapToBookEntity(CreateBookRequestDTO requestDTO);

    @Mapping(source = "authorEntity.id", target = "authorId", qualifiedByName = "mapToAuthorId")
    BookDTO mapToBookDTO(BookEntity bookEntity);

    @Named("mapToAuthorId")
    default AuthorId mapToAuthorId(Long id) {
        return AuthorId.of(id);
    }

    @Named("mapFromAuthorId")
    default Long mapFromAuthorId(AuthorId id) {
        return id.value();
    }

    default Short mapOptionalToShort(Optional<Short> value) {
        return value.orElse(null);
    }

    default Optional<Short> mapShortToOptional(Short value) {
        return Optional.ofNullable(value);
    }

    default BookId mapBookId(Long id) {
        return BookId.of(id);
    }

    default Isbn13 mapToIsbn13(String isbn) {
        return Isbn13.of(isbn);
    }

    default String mapFromIsbn13(Isbn13 isbn) {
        return isbn.value();
    }
}
