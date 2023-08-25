package com.cgdpd.library.dto.book.copy;

import static com.cgdpd.library.dto.book.copy.TrackingStatus.AVAILABLE;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.CHECKED_OUT;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.LOST;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.ON_HOLD;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.REFERENCE;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.RETIRED;
import static com.cgdpd.library.util.OptionalUtil.actualOrEmpty;
import static com.cgdpd.library.validation.Validator.required;
import static com.cgdpd.library.validation.Validator.validate;

import com.cgdpd.library.type.BookCopyId;
import com.cgdpd.library.type.BookId;
import com.cgdpd.library.type.UserId;
import java.util.Optional;
import java.util.Set;
import lombok.Builder;

@Builder(toBuilder = true)
public record BookCopyDTO(BookCopyId id,
                          BookId bookId,
                          TrackingStatus trackingStatus,
                          Optional<UserId> userId) {

    private static final Set<TrackingStatus> MANDATORY_USER_ID_WITH_STATUS = Set.of(
          ON_HOLD,
          CHECKED_OUT);
    private static final Set<TrackingStatus> OPTIONAL_USER_ID_WITH_STATUS = Set.of(
          LOST);

    public BookCopyDTO(BookCopyId id,
                       BookId bookId,
                       TrackingStatus trackingStatus,
                       Optional<UserId> userId) {
        this.id = required("id", id);
        this.bookId = required("bookId", bookId);
        this.trackingStatus = required("trackingStatus", trackingStatus);
        this.userId = actualOrEmpty(userId);
        validateUserIdCanBePresent();
    }

    public BookCopyDTO available() {
        return changeStatusIfAllowed(AVAILABLE).userId(Optional.empty()).build();
    }

    public BookCopyDTO onHold(UserId userId) {
        return changeStatusIfAllowed(ON_HOLD).userId(Optional.of(userId)).build();
    }

    public BookCopyDTO checkedOut(UserId userId) {
        return changeStatusIfAllowed(CHECKED_OUT).userId(Optional.of(userId)).build();
    }

    public BookCopyDTO lost() {
        return changeStatusIfAllowed(LOST).build();
    }

    public BookCopyDTO reference() {
        return changeStatusIfAllowed(REFERENCE).build();
    }

    public BookCopyDTO retired() {
        return changeStatusIfAllowed(RETIRED).build();
    }

    private BookCopyDTO.BookCopyDTOBuilder changeStatusIfAllowed(TrackingStatus nextStatus) {
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
