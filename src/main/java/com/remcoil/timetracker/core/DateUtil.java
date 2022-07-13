package com.remcoil.timetracker.core;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;

public class DateUtil {
    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of("Europe/Moscow"));
    }

    public static LocalDateTime startDay(LocalDateTime day) {
        return day.toLocalDate().atStartOfDay();
    }

    public static LocalDateTime startDay() {
        return startDay(now());
    }

    public static LocalDateTime endDay(LocalDateTime day) {
        return day.plusDays(1L).toLocalDate().atStartOfDay();
    }

    public static LocalDateTime endDay() {
        return endDay(now());
    }

    public static LocalDateTime startMonth(LocalDateTime day) {
        return YearMonth.from(day).atDay(1).atStartOfDay();
    }

    public static LocalDateTime startMonth() {
        return startMonth(now());
    }

    public static LocalDateTime endMonth(LocalDateTime day) {
        return YearMonth.from(day).atEndOfMonth().atStartOfDay();
    }

    public static LocalDateTime endMonth() {
        return endMonth(now());
    }
}
