package com.cgdpd.library.dto.book.copy;

import static com.cgdpd.library.model.book.copy.TrackingStatus.AVAILABLE;
import static com.cgdpd.library.model.book.copy.TrackingStatus.CHECKED_OUT;
import static com.cgdpd.library.model.book.copy.TrackingStatus.LOST;
import static com.cgdpd.library.model.book.copy.TrackingStatus.ON_HOLD;
import static com.cgdpd.library.model.book.copy.TrackingStatus.REFERENCE;
import static com.cgdpd.library.model.book.copy.TrackingStatus.RETIRED;
import static org.assertj.core.api.Assertions.assertThat;

import com.cgdpd.library.model.book.copy.TrackingStatus;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class TrackingStatusTest {

    @ParameterizedTest
    @EnumSource(TrackingStatus.class)
    void should_return_true_when_transition_is_allowed(TrackingStatus status) {
        // given
        var allowedStatusTransitions = getAllowedTransitions(status);

        for (var allowedStatusTransition : allowedStatusTransitions) {
            // when
            var result = status.isTransitionAllowed(allowedStatusTransition);

            // then
            assertThat(result).isTrue();
        }
    }

    @ParameterizedTest
    @EnumSource(TrackingStatus.class)
    void should_return_false_when_transition_is_not_allowed(TrackingStatus status) {
        // given
        var notAllowedStatusTransitions = getNotAllowedStatusTransitions(status);

        for (var notAllowedStatusTransition : notAllowedStatusTransitions) {
            // when
            var result = status.isTransitionAllowed(notAllowedStatusTransition);

            // then
            assertThat(result).isFalse();
        }
    }

    private Set<TrackingStatus> getNotAllowedStatusTransitions(TrackingStatus status) {
        var allowedStatusTransitions = getAllowedTransitions(status);
        return Arrays.stream(TrackingStatus.values())
              .filter(otherStatus -> !allowedStatusTransitions.contains(otherStatus))
              .collect(Collectors.toSet());
    }

    private Set<TrackingStatus> getAllowedTransitions(TrackingStatus status) {
        return switch (status) {
            case BEING_PROCESSED -> Set.of(AVAILABLE, REFERENCE, RETIRED);
            case AVAILABLE -> Set.of(CHECKED_OUT, ON_HOLD, LOST, RETIRED);
            case ON_HOLD -> Set.of(CHECKED_OUT, AVAILABLE);
            case CHECKED_OUT -> Set.of(LOST, AVAILABLE);
            case LOST, REFERENCE -> Set.of(AVAILABLE, RETIRED);
            case RETIRED -> Set.of();
        };
    }
}
