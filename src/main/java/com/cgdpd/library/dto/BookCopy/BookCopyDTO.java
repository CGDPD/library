package com.cgdpd.library.dto.BookCopy;

import static com.cgdpd.library.validation.Validator.required;

import com.cgdpd.library.entity.TrackingStatus;
import java.util.Optional;
import lombok.Builder;

@Builder
public record BookCopyDTO(Long id,
                          Long bookId,
                          TrackingStatus trackingStatus,
                          Optional<Long> userId) {

    public BookCopyDTO(Long id,
                       Long bookId,
                       TrackingStatus trackingStatus,
                       Optional<Long> userId) {
        this.id = required("id", id);
        this.bookId = required("bookId", bookId);
        this.trackingStatus = required("trackingStatus", trackingStatus);
        this.userId = userId;
    }
}
