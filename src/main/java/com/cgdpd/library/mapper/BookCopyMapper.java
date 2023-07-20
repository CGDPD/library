package com.cgdpd.library.mapper;

import com.cgdpd.library.dto.BookCopy.BookCopyDTO;
import com.cgdpd.library.entity.BookCopyEntity;
import com.cgdpd.library.entity.TrackingStatus;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {

    @Mapping(target = "bookId", source = "bookEntity.id")
    @Mapping(target = "userId", source = "userEntity.id", qualifiedByName = "mapUserEntityToOptional")
    @Mapping(target = "trackingStatus", source = "trackingStatus", qualifiedByName = "stringToTrackingStatus")
    BookCopyDTO mapToBookCopyDto(BookCopyEntity bookCopyEntity);

    @Named("stringToTrackingStatus")
    default TrackingStatus stringToTrackingStatus(String value) {
        if (value == null) {
            return null;
        }

        Map<String, TrackingStatus> trackingStatusMap = Arrays.stream(TrackingStatus.values())
              .collect(Collectors.toMap(status -> status.getStatus().toUpperCase(), Function.identity()));

        return trackingStatusMap.get(value.toUpperCase());
    }

    @Named("mapUserEntityToOptional")
    default Optional<Long> mapUserEntityToOptional(Long value) {
        return Optional.ofNullable(value);
    }
}
