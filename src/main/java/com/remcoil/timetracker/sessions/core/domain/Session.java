package com.remcoil.timetracker.sessions.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.remcoil.timetracker.core.DateUtil;
import com.remcoil.timetracker.progress.core.models.Continuous;
import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.users.core.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Session implements Continuous {
    private final long id;
    @JsonIgnore
    private final User user;
    private final Project project;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime endTime;

    @Override
    public LocalDate getDate() {
        return startTime.toLocalDate();
    }

    @Override
    public Duration calculateDuration() {
        LocalDateTime end = endTime != null ? endTime : DateUtil.now();
        return Duration.between(startTime, end);
    }
}
