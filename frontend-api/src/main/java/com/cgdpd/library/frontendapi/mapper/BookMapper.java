package com.cgdpd.library.frontendapi.mapper;

import com.cgdpd.library.catalog.domain.book.dto.DetailedBookDto;
import com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus;
import com.cgdpd.library.frontendapi.dto.BookAvailability;
import com.cgdpd.library.frontendapi.dto.BookViewDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "trackingStatusList", target = "bookAvailability")
    BookViewDto mapToBookView(DetailedBookDto detailedBookDto);

    default BookAvailability mapToBookAvailability(List<TrackingStatus> trackingStatusList) {
        return BookAvailability.fromTrackingStatusList(trackingStatusList);
    }
}
