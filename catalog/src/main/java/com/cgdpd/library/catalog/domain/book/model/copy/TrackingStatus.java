package com.cgdpd.library.catalog.domain.book.model.copy;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public enum TrackingStatus {
    BEING_PROCESSED(false, false),
    AVAILABLE(false, false),
    CHECKED_OUT(true, false),
    ON_HOLD(true, false),
    LOST(false, true),
    RETIRED(false, false),
    REFERENCE(false, false);

    private static final Map<TrackingStatus, Set<TrackingStatus>> allowedTransitions;

    static {
        allowedTransitions = Map.ofEntries(
              Map.entry(BEING_PROCESSED, Set.of(AVAILABLE, REFERENCE, RETIRED)),
              Map.entry(AVAILABLE, Set.of(CHECKED_OUT, ON_HOLD, LOST, RETIRED)),
              Map.entry(ON_HOLD, Set.of(CHECKED_OUT, AVAILABLE)),
              Map.entry(CHECKED_OUT, Set.of(LOST, AVAILABLE)),
              Map.entry(LOST, Set.of(AVAILABLE, RETIRED)),
              Map.entry(REFERENCE, Set.of(AVAILABLE, RETIRED)),
              Map.entry(RETIRED, Collections.emptySet())
        );
    }

    private final boolean requiresUser;
    private final boolean canHaveUser;

    TrackingStatus(boolean requiresUser, boolean canHaveUser) {
        this.requiresUser = requiresUser;
        this.canHaveUser = canHaveUser;
    }


    public boolean isTransitionAllowed(TrackingStatus nextStatus) {
        return allowedTransitions.get(this).contains(nextStatus);
    }

    public boolean requiresUser() {
        return requiresUser;
    }

    public boolean optionalUser() {
        return canHaveUser;
    }
}
