package com.cgdpd.library.mapper;

import com.cgdpd.library.entity.BookCopyEntity;
import com.cgdpd.library.model.book.copy.BookCopy;
import com.cgdpd.library.model.book.copy.TrackingStatus;
import com.cgdpd.library.type.BookCopyId;
import com.cgdpd.library.type.BookId;
import com.cgdpd.library.type.UserId;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "mapBookCopyId")
    @Mapping(target = "bookId", source = "bookEntity.id", qualifiedByName = "mapBookId")
    @Mapping(target = "trackingStatus", source = "trackingStatus", qualifiedByName = "stringToTrackingStatus")
    @Mapping(target = "userId", source = "userEntity.id", qualifiedByName = "mapUserId")
    BookCopy mapToBookCopy(BookCopyEntity bookCopyEntity);

    @Named("mapBookCopyId")
    default BookCopyId mapBookCopyId(Long id) {
        return BookCopyId.of(id);
    }

    @Named("mapBookId")
    default BookId mapBookId(Long value) {
        return BookId.of(value);
    }

    @Named("stringToTrackingStatus")
    default TrackingStatus stringToTrackingStatus(String trackingStatus) {
        return TrackingStatus.valueOf(trackingStatus.toUpperCase());
    }

    @Named("mapUserId")
    default Optional<UserId> mapUserId(Long value) {
        return Optional.ofNullable(value).map(UserId::of);
    }
}
