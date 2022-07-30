package com.remcoil.timetracker.progress.core.models;

import com.remcoil.timetracker.core.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ProgressPeriod {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public static ProgressPeriod day() {
        return new ProgressPeriod(
                DateUtil.startDay(),
                DateUtil.endDay()
        );
    }

    public static ProgressPeriod month() {
        return new ProgressPeriod(
                DateUtil.startMonth(),
                DateUtil.endMonth()
        );
    }
}
