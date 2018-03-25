package com.icanmining.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public final class TimeUtils {

    public static LocalDateTime utcNowDateTime() {
        return ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
    }

    private TimeUtils() {
        throw new AssertionError();
    }
}
