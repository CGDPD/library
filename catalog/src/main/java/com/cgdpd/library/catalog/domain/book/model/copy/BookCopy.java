package com.cgdpd.library.catalog.domain.book.model.copy;


import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.AVAILABLE;
import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.CHECKED_OUT;
import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.LOST;
import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.ON_HOLD;
import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.REFERENCE;
import static com.cgdpd.library.catalog.domain.book.model.copy.TrackingStatus.RETIRED;
import static com.cgdpd.library.common.util.OptionalUtil.actualOrEmpty;
import static com.cgdpd.library.common.validation.Validator.required;
import static com.cgdpd.library.common.validation.Validator.validate;

import com.cgdpd.library.catalog.domain.book.model.BookId;

import lombok.Builder;

import java.util.Optional;

@Builder(toBuilder = true)
public record BookCopy(BookCopyId id,
                       BookId bookId,
                       TrackingStatus trackingStatus,
                       Optional<UserId> userId) {


    public BookCopy(BookCopyId id,
                    BookId bookId,
                    TrackingStatus trackingStatus,
                    Optional<UserId> userId) {
        this.id = required("id", id);
        this.bookId = required("bookId", bookId);
        this.trackingStatus = required("trackingStatus", trackingStatus);
        this.userId = actualOrEmpty(userId);
        validateUserIdCanBePresent();
    }

    public BookCopy available() {
        return changeStatusIfAllowed(AVAILABLE).userId(Optional.empty()).build();
    }

    public BookCopy onHold(UserId userId) {
        return changeStatusIfAllowed(ON_HOLD).userId(Optional.of(userId)).build();
    }

    public BookCopy checkedOut(UserId userId) {
        return changeStatusIfAllowed(CHECKED_OUT).userId(Optional.of(userId)).build();
    }

    public BookCopy lost() {
        return changeStatusIfAllowed(LOST).build();
    }

    public BookCopy reference() {
        return changeStatusIfAllowed(REFERENCE).build();
    }

    public BookCopy retired() {
        return changeStatusIfAllowed(RETIRED).build();
    }

    private BookCopyBuilder changeStatusIfAllowed(TrackingStatus nextStatus) {
        validate(() -> !trackingStatus.isTransitionAllowed(nextStatus),
              "Transition from status %s, to status %s is not allowed",
              trackingStatus,
              nextStatus);

        return toBuilder().trackingStatus(nextStatus);
    }

    private void validateUserIdCanBePresent() {
        if (trackingStatus.requiresUser()) {
            validate(userId::isEmpty, "When status is %s the user id is mandatory", trackingStatus);
        } else if (!trackingStatus.optionalUser()) {
            validate(userId::isPresent,
                  "When status is %s the user id should be empty",
                  trackingStatus);
        }
    }
}
