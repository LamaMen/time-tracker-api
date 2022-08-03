package com.remcoil.timetracker.progress.core.models;

import com.remcoil.timetracker.core.DateUtil;
import com.remcoil.timetracker.progress.core.exceptions.BothEndsMustBePassedException;
import com.remcoil.timetracker.progress.core.exceptions.StartDateAfterEndException;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProgressPeriod {
    private final LocalDate start;
    private final LocalDate end;

    public ProgressPeriod(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) throw new StartDateAfterEndException();
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return DateUtil.startDay(start);
    }

    public LocalDateTime getEnd() {
        return DateUtil.endDay(end);
    }

    public static ProgressPeriod orDefault(
            @Nullable LocalDate start,
            @Nullable LocalDate end
    ) {
        if ((start == null) != (end == null)) throw new BothEndsMustBePassedException();
        return start == null ? ProgressPeriod.month() : new ProgressPeriod(start, end);
    }


    public static ProgressPeriod day() {
        return new ProgressPeriod(
                DateUtil.today(),
                DateUtil.today()
        );
    }

    public static ProgressPeriod month() {
        return new ProgressPeriod(
                DateUtil.startMonth(),
                DateUtil.endMonth()
        );
    }
}
