package com.cgdpd.library.frontendapi.dto;

import com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus;

import java.util.Collection;
import java.util.Comparator;

public enum BookAvailability {
    AVAILABLE(1),
    RENTED(2),
    UNAVAILABLE(3);

    private final short priority;

    BookAvailability(int priority) {
        this.priority = (short) priority;
    }

    public static BookAvailability fromTrackingStatusList(Collection<TrackingStatus> trackingStatus) {
        return trackingStatus.stream()
              .map(BookAvailability::fromTrackingStatus)
              .min(Comparator.comparing(e -> e.priority))
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