package com.remcoil.timetracker.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;

public class DateUtil {
    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of("Europe/Moscow"));
    }

    public static LocalDate today() {
        return now().toLocalDate();
    }

    public static LocalDateTime startDay(LocalDate day) {
        return day.atStartOfDay();
    }

    public static LocalDateTime startDay() {
        return startDay(now().toLocalDate());
    }

    public static LocalDateTime endDay(LocalDate day) {
        return day.plusDays(1L).atStartOfDay();
    }

    public static LocalDateTime endDay() {
        return endDay(now().toLocalDate());
    }

    public static LocalDate startMonth(LocalDate day) {
        return YearMonth.from(day).atDay(1);
    }

    public static LocalDate startMonth() {
        return startMonth(now().toLocalDate());
    }

    public static LocalDate endMonth(LocalDate day) {
        return YearMonth.from(day).atEndOfMonth();
    }

    public static LocalDate endMonth() {
        return endMonth(now().toLocalDate());
    }
}
