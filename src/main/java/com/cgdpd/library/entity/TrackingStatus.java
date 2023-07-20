package com.cgdpd.library.entity;

import java.util.HashMap;
import java.util.Map;

public enum TrackingStatus {
    BEING_PROCESSED("Being Processed"),
    AVAILABLE("Available"),
    ON_HOLD("On Hold"),
    CHECKED_OUT("Checked Out"),
    LOST("Lost"),
    RETIRED("Retired"),
    REFERENCE("Reference");

    private final String status;
    private final Map<TrackingStatus, Boolean> nextStatuses;

    TrackingStatus(String status) {
        this.status = status;
        this.nextStatuses = new HashMap<>();
    }

    public String getStatus() {
        return status;
    }

    public Map<TrackingStatus, Boolean> getNextStatuses() {
        return nextStatuses;
    }

    public void addNextStatus(TrackingStatus nextStatus, boolean available) {
        nextStatuses.put(nextStatus, available);
    }

    public boolean isNextStatusAvailable(TrackingStatus nextStatus) {
        return nextStatuses.getOrDefault(nextStatus, false);
    }

    static {
        BEING_PROCESSED.addNextStatus(AVAILABLE, true);
        BEING_PROCESSED.addNextStatus(REFERENCE, true);
        BEING_PROCESSED.addNextStatus(RETIRED, true);

        AVAILABLE.addNextStatus(CHECKED_OUT, true);
        AVAILABLE.addNextStatus(ON_HOLD, true);
        AVAILABLE.addNextStatus(LOST, true);
        AVAILABLE.addNextStatus(RETIRED, true);

        ON_HOLD.addNextStatus(CHECKED_OUT, true);
        ON_HOLD.addNextStatus(AVAILABLE, true);

        CHECKED_OUT.addNextStatus(LOST, true);
        CHECKED_OUT.addNextStatus(AVAILABLE, true);

        LOST.addNextStatus(AVAILABLE, true);
        LOST.addNextStatus(RETIRED, true);

        REFERENCE.addNextStatus(AVAILABLE, true);
        REFERENCE.addNextStatus(RETIRED, true);
    }
}
