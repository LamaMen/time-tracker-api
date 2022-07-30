package com.remcoil.timetracker.progress.core;

import com.remcoil.timetracker.core.DateUtil;
import com.remcoil.timetracker.progress.core.models.Progress;
import com.remcoil.timetracker.progress.core.models.ProgressPeriod;
import com.remcoil.timetracker.progress.core.models.ProjectDuration;
import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.sessions.core.domain.Session;
import com.remcoil.timetracker.sessions.core.domain.SessionService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProgressUseCase {
    private final SessionService sessionService;

    public ProgressUseCase(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public List<Progress> getTodayProgress(UUID userId) {
        return getProgressByPeriod(userId, ProgressPeriod.day());
    }

    public List<Progress> getGeneralProgressByMonth() {
        return getProgressByPeriod(null, ProgressPeriod.month());
    }

    private List<Progress> getProgressByPeriod(@Nullable UUID userId, ProgressPeriod period) {
        List<Session> sessions = sessionService.getByPeriod(userId, period.getStart(), period.getEnd());
        Map<Project, ProjectDuration> durations = calculateDiff(sessions);
        return mapDurationsToProgress(durations);
    }

    public Map<LocalDate, List<Progress>> getMonthProgress(UUID userId) {
        List<Session> sessions = sessionService.getByPeriod(userId, DateUtil.startMonth(), DateUtil.endMonth());
        Map<LocalDate, List<Session>> groupedSessions = sessions.stream().collect(Collectors.groupingBy((s) -> s.getStartTime().toLocalDate()));

        Map<LocalDate, List<Progress>> progress = new HashMap<>();

        groupedSessions.forEach((d, s) -> {
            Map<Project, ProjectDuration> durations = calculateDiff(s);
            List<Progress> p = mapDurationsToProgress(durations);
            progress.put(d, p);
        });

        return progress;
    }

    private List<Progress> mapDurationsToProgress(Map<Project, ProjectDuration> durations) {
        return durations.entrySet().stream()
                .map(p -> new Progress(p.getKey(), p.getValue()))
                .collect(Collectors.toList());
    }

    private Map<Project, ProjectDuration> calculateDiff(List<Session> sessions) {
        return sessions.stream().collect(Collectors
                .groupingBy(Session::getProject, new ProjectDurationCollector()));
    }
}
