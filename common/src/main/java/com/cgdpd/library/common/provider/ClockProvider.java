package com.cgdpd.library.common.provider;

import java.time.Clock;

public class ClockProvider {
    private static final Clock clock = Clock.systemUTC();

    public static Clock utcClock() {
        return clock;
    }
}
