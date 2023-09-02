package com.cgdpd.library.common.util;

import java.util.Optional;

public final class OptionalUtil {

    private OptionalUtil() {
    }

    public static <T> Optional<T> actualOrEmpty(Optional<T> actual) {
        return actual.isEmpty() ? Optional.empty() : actual;
    }
}
