package com.cgdpd.library.model.book.copy;

import static com.cgdpd.library.model.book.copy.TrackingStatus.AVAILABLE;
import static com.cgdpd.library.model.book.copy.TrackingStatus.CHECKED_OUT;
import static com.cgdpd.library.model.book.copy.TrackingStatus.LOST;
import static com.cgdpd.library.model.book.copy.TrackingStatus.ON_HOLD;
import static com.cgdpd.library.model.book.copy.TrackingStatus.REFERENCE;
import static com.cgdpd.library.model.book.copy.TrackingStatus.RETIRED;
import static com.cgdpd.library.util.OptionalUtil.actualOrEmpty;
import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.validate;

import com.cgdpd.library.type.BookCopyId;
import com.cgdpd.library.type.BookId;
import com.cgdpd.library.type.UserId;

import lombok.Builder;

import java.util.Optional;
import java.util.Set;

@Builder(toBuilder = true)
public record BookCopy(BookCopyId id,
                       BookId bookId,
                       TrackingStatus trackingStatus,
                       Optional<UserId> userId) {

    private static final Set<TrackingStatus> MANDATORY_USER_ID_WITH_STATUS = Set.of(
          ON_HOLD,
          CHECKED_OUT);
    private static final Set<TrackingStatus> OPTIONAL_USER_ID_WITH_STATUS = Set.of(
          LOST);

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

    private BookCopy.BookCopyBuilder changeStatusIfAllowed(TrackingStatus nextStatus) {
        validate(() -> !trackingStatus.isTransitionAllowed(nextStatus),
              "Transition from status %s, to status %s is not allowed",
              trackingStatus,
              nextStatus);

        return toBuilder().trackingStatus(nextStatus);
    }

    private void validateUserIdCanBePresent() {
        if (MANDATORY_USER_ID_WITH_STATUS.contains(trackingStatus)) {
            validate(userId::isEmpty, "When status is %s the user id is mandatory", trackingStatus);
        } else if (!OPTIONAL_USER_ID_WITH_STATUS.contains(trackingStatus)) {
            validate(userId::isPresent,
                  "When status is %s the user id should be empty",
                  trackingStatus);
        }
    }
}
