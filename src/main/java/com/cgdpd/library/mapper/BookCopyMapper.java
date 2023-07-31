package com.cgdpd.library.mapper;

import com.cgdpd.library.dto.book.copy.BookCopyDTO;
import com.cgdpd.library.entity.BookCopyEntity;
import com.cgdpd.library.dto.book.copy.TrackingStatus;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {

    @Mapping(target = "bookId", source = "bookEntity.id")
    @Mapping(target = "userId", source = "userEntity.id", qualifiedByName = "mapUserEntityId")
    @Mapping(target = "trackingStatus", source = "trackingStatus", qualifiedByName = "stringToTrackingStatus")
    BookCopyDTO mapToBookCopyDto(BookCopyEntity bookCopyEntity);



    @Named("stringToTrackingStatus")
    default TrackingStatus stringToTrackingStatus(String trackingStatus) {
        return TrackingStatus.valueOf(trackingStatus.toUpperCase());
    }

    @Named("mapUserEntityId")
    default Optional<Long> mapUserEntityId(Long value) {
        return Optional.ofNullable(value);
    }
}
