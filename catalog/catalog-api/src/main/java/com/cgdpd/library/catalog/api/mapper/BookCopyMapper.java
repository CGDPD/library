package com.cgdpd.library.catalog.api.mapper;


import com.cgdpd.library.catalog.api.entity.BookCopyEntity;
import com.cgdpd.library.catalog.domain.book.model.BookId;
import com.cgdpd.library.catalog.domain.book.model.copy.BookCopy;

import com.cgdpd.library.catalog.domain.book.model.copy.BookCopyId;

import com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus;

import com.cgdpd.library.catalog.domain.book.model.copy.UserId;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "mapBookCopyId")
    @Mapping(target = "bookId", source = "bookEntity.id", qualifiedByName = "mapBookId")
    @Mapping(target = "trackingStatus", source = "trackingStatus", qualifiedByName = "stringToTrackingStatus")
    @Mapping(target = "userId", source = "userId", qualifiedByName = "mapUserId")
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
