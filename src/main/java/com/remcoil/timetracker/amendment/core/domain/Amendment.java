package com.remcoil.timetracker.amendment.core.domain;

import com.remcoil.timetracker.progress.core.models.Continuous;
import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.users.core.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Amendment implements Continuous {
    private final long id;
    private final User user;
    private final Project project;
    private final LocalDate date;
    private final boolean isPositive;
    private final int hours;
    private final int minutes;

    @Override
    public Duration calculateDuration() {
        int sign = isPositive ? 1 : -1;
        int onlyMinutes = (minutes + hours * 60) * sign;
        return Duration.ofMinutes(onlyMinutes);
    }
}