package com.cgdpd.library.catalog.domain.book.dto;

import static com.cgdpd.library.dto.book.BookAvailability.AVAILABLE;
import static com.cgdpd.library.dto.book.BookAvailability.RENTED;
import static com.cgdpd.library.dto.book.BookAvailability.UNAVAILABLE;
import static com.cgdpd.library.dto.book.BookAvailability.fromTrackingStatus;
import static com.cgdpd.library.dto.book.BookAvailability.fromTrackingStatuses;
import static java.util.Collections.emptyList;

import com.cgdpd.library.model.book.copy.TrackingStatus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class BookAvailabilityTest {


    private static Stream<Arguments> trackingStatusToBookAvailabilityArguments() {
        return Stream.of(
              Arguments.of(TrackingStatus.BEING_PROCESSED, UNAVAILABLE),
              Arguments.of(TrackingStatus.AVAILABLE, AVAILABLE),
              Arguments.of(TrackingStatus.CHECKED_OUT, RENTED),
              Arguments.of(TrackingStatus.ON_HOLD, RENTED),
              Arguments.of(TrackingStatus.LOST, UNAVAILABLE),
              Arguments.of(TrackingStatus.RETIRED, UNAVAILABLE),
              Arguments.of(TrackingStatus.REFERENCE, UNAVAILABLE)
        );
    }

    private static Stream<Arguments> multipleTrackingStatusToBookAvailabilityArguments() {
        return Stream.of(
              Arguments.of(List.of(
                          TrackingStatus.BEING_PROCESSED,
                          TrackingStatus.AVAILABLE,
                          TrackingStatus.LOST),
                    AVAILABLE),
              Arguments.of(List.of(
                          TrackingStatus.BEING_PROCESSED,
                          TrackingStatus.LOST),
                    UNAVAILABLE),
              Arguments.of(List.of(
                          TrackingStatus.RETIRED,
                          TrackingStatus.LOST),
                    UNAVAILABLE)
        );
    }

    @ParameterizedTest()
    @MethodSource("trackingStatusToBookAvailabilityArguments")
    void should_convert_tracking_status_to_book_availability(TrackingStatus givenTrackingStatus,
                                                             BookAvailability expectedBookAvailability) {
        // when
        var resultBookAvailability = fromTrackingStatus(givenTrackingStatus);

        // then
        assertThat(resultBookAvailability).isEqualTo(expectedBookAvailability);
    }

    @ParameterizedTest()
    @MethodSource("multipleTrackingStatusToBookAvailabilityArguments")
    void should_convert_multiple_tracking_status_to_book_availability_applying_priority(
          List<TrackingStatus> givenTrackingStatuses,
          BookAvailability expectedBookAvailability) {

        // when
        var resultBookAvailability = fromTrackingStatuses(givenTrackingStatuses);

        // then
        assertThat(resultBookAvailability).isEqualTo(expectedBookAvailability);
    }

    @Test
    void should_convert_empty_list_tracking_status_to_unavailable_book_availability_applying_priority() {
        // when
        var resultBookAvailability = fromTrackingStatuses(emptyList());

        // then
        assertThat(resultBookAvailability).isEqualTo(UNAVAILABLE);
    }
}
