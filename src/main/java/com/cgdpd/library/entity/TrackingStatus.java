package com.cgdpd.library.entity;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum TrackingStatus {
    AVAILABLE("Available"),
    ON_HOLD("On Hold"),
    CHECKED_OUT("Checked Out"),
    LOST("Lost"),
    RETIRED("Retired"),
    REFERENCE("Reference");

    private final String status;
    private static final Map<TrackingStatus, Set<TrackingStatus>> nextStatuses = new HashMap<>();

    TrackingStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Map<TrackingStatus, Set<TrackingStatus>> getNextStatuses() {
        return nextStatuses;
    }

    public void addNextStatus(TrackingStatus nextStatus) {
        nextStatuses.computeIfAbsent(this, k -> EnumSet.noneOf(TrackingStatus.class))
              .add(nextStatus);
    }

    public boolean isNextStatusAvailable(TrackingStatus nextStatus) {
        return nextStatuses.getOrDefault(this, EnumSet.noneOf(TrackingStatus.class))
              .contains(nextStatus);
    }

    public TrackingStatus getNextStatus() {
        Set<TrackingStatus> nextStatusSet = nextStatuses.getOrDefault(this, EnumSet.noneOf(TrackingStatus.class));
        if (nextStatusSet.isEmpty()) {
            return null;
        }
        return nextStatusSet.iterator().next();
    }

    static {
        AVAILABLE.addNextStatus(CHECKED_OUT);
        AVAILABLE.addNextStatus(ON_HOLD);
        AVAILABLE.addNextStatus(LOST);
        AVAILABLE.addNextStatus(RETIRED);

        ON_HOLD.addNextStatus(CHECKED_OUT);
        ON_HOLD.addNextStatus(AVAILABLE);

        CHECKED_OUT.addNextStatus(LOST);
        CHECKED_OUT.addNextStatus(AVAILABLE);

        LOST.addNextStatus(AVAILABLE);
        LOST.addNextStatus(RETIRED);

        REFERENCE.addNextStatus(AVAILABLE);
        REFERENCE.addNextStatus(RETIRED);
    }
}
