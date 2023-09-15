package com.cgdpd.library.frontendapi.dto;

import com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus;

import java.util.Comparator;
import java.util.List;

public enum BookAvailability {
    AVAILABLE(3),
    RENTED(2),
    UNAVAILABLE(1);

    private final short priority;

    BookAvailability(int priority) {
        this.priority = (short) priority;
    }

    public static BookAvailability fromTrackingStatusList(List<TrackingStatus> trackingStatusList) {
        return trackingStatusList.stream()
              .map(BookAvailability::fromTrackingStatus)
              .max(Comparator.comparing(e -> e.priority))
              .orElse(UNAVAILABLE);
    }

    public static BookAvailability fromTrackingStatus(TrackingStatus trackingStatus) {
        return switch (trackingStatus) {
            case AVAILABLE -> AVAILABLE;
            case CHECKED_OUT, ON_HOLD -> RENTED;
            case BEING_PROCESSED, REFERENCE, RETIRED, LOST -> UNAVAILABLE;
        };
    }

}
