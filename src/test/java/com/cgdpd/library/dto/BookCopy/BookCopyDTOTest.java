package com.cgdpd.library.dto.BookCopy;

import static com.cgdpd.library.BookCopyTestData.aBookCopy;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.AVAILABLE;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.BEING_PROCESSED;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.CHECKED_OUT;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.LOST;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.ON_HOLD;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.REFERENCE;
import static com.cgdpd.library.dto.book.copy.TrackingStatus.RETIRED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cgdpd.library.exceptions.ValidationException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class BookCopyDTOTest {

    @Test
    void should_build_book_with_user_id_and_allowed_status() {
        // when
        var book = aBookCopy()
              .trackingStatus(CHECKED_OUT)
              .userId(Optional.of(4L))
              .build();

        // then
        assertThat(book).isNotNull();
    }

    @Test
    void should_throw_exception_when_user_id_is_empty_but_status_requires_it() {
        // then
        assertThatThrownBy(
              () -> aBookCopy()
                    .trackingStatus(CHECKED_OUT)
                    .userId(Optional.empty())
                    .build())
              .isInstanceOf(ValidationException.class);
    }

    @Test
    void should_throw_exception_when_user_id_is_present_with_wrong_status() {
        // then
        assertThatThrownBy(
              () -> aBookCopy()
                    .trackingStatus(REFERENCE)
                    .userId(Optional.of(4L))
                    .build())
              .isInstanceOf(ValidationException.class);
    }

    @Test
    void should_change_status_when_transition_is_allowed() {
        // given
        var book = aBookCopy()
              .trackingStatus(CHECKED_OUT)
              .build();

        // when
        var result = book.available();

        // then
        assertThat(result.trackingStatus()).isEqualTo(AVAILABLE);
    }

    @Test
    void should_change_status_to_on_hold_when_transition_is_allowed() {
        // given
        var book = aBookCopy()
              .trackingStatus(AVAILABLE)
              .userId(Optional.empty())
              .build();

        // when
        var result = book.onHold(4L);

        // then
        assertThat(result.trackingStatus()).isEqualTo(ON_HOLD);
    }

    @Test
    void should_throw_exception_when_on_hold_transition_is_not_allowed() {
        // given
        var book = aBookCopy()
              .trackingStatus(BEING_PROCESSED)
              .userId(Optional.empty())
              .build();

        // then
        assertThatThrownBy(() -> book.onHold(4L))
              .isInstanceOf(ValidationException.class);
    }

    @Test
    void should_change_status_to_checked_out_when_transition_is_allowed() {
        // given
        var book = aBookCopy()
              .trackingStatus(AVAILABLE)
              .userId(Optional.empty())
              .build();

        // when
        var result = book.checkedOut(4L);

        // then
        assertThat(result.trackingStatus()).isEqualTo(CHECKED_OUT);
    }

    @Test
    void should_throw_exception_when_checked_out_transition_is_not_allowed() {
        // given
        var book = aBookCopy()
              .trackingStatus(LOST)
              .build();

        // then
        assertThatThrownBy(() -> book.checkedOut(4L))
              .isInstanceOf(ValidationException.class);
    }

    @Test
    void should_change_status_to_lost_when_transition_is_allowed() {
        // given
        var book = aBookCopy()
              .trackingStatus(CHECKED_OUT)
              .build();

        // when
        var result = book.lost();

        // then
        assertThat(result.trackingStatus()).isEqualTo(LOST);
    }

    @Test
    void should_throw_exception_when_lost_transition_is_not_allowed() {
        // given
        var book = aBookCopy()
              .trackingStatus(ON_HOLD)
              .build();

        // then
        assertThatThrownBy(book::lost)
              .isInstanceOf(ValidationException.class);
    }

    @Test
    void should_change_status_to_reference_when_transition_is_allowed() {
        // given
        var book = aBookCopy()
              .trackingStatus(BEING_PROCESSED)
              .userId(Optional.empty())
              .build();

        // when
        var result = book.reference();

        // then
        assertThat(result.trackingStatus()).isEqualTo(REFERENCE);
    }

    @Test
    void should_throw_exception_when_reference_transition_is_not_allowed() {
        // given
        var book = aBookCopy()
              .trackingStatus(AVAILABLE)
              .userId(Optional.empty())
              .build();

        // then
        assertThatThrownBy(book::reference)
              .isInstanceOf(ValidationException.class);
    }

    @Test
    void should_change_status_to_retired_when_transition_is_allowed() {
        // given
        var book = aBookCopy()
              .trackingStatus(LOST)
              .userId(Optional.empty())
              .build();

        // when
        var result = book.retired();

        // then
        assertThat(result.trackingStatus()).isEqualTo(RETIRED);
    }

    @Test
    void should_throw_exception_when_retired_transition_is_not_allowed() {
        // given
        var book = aBookCopy()
              .trackingStatus(ON_HOLD)
              .build();

        // then
        assertThatThrownBy(book::retired)
              .isInstanceOf(ValidationException.class);
    }

}
