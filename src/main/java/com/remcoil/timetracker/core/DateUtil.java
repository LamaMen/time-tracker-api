package com.remcoil.timetracker.core;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {
    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of("Europe/Moscow"));
    }

    public static LocalDateTime todayNight() {
        return LocalDateTime.now(ZoneId.of("Europe/Moscow"))
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
    }
}
