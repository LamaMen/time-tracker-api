package com.remcoil.timetracker.progress.core;

import com.remcoil.timetracker.progress.core.models.Progress;
import com.remcoil.timetracker.progress.core.models.ProgressPeriod;
import com.remcoil.timetracker.progress.core.models.ProjectDuration;
import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.sessions.core.domain.Session;
import com.remcoil.timetracker.sessions.core.domain.SessionService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProgressUseCase {
    private final SessionService sessionService;

    public ProgressUseCase(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public List<Progress> getProgressByPeriod(@Nullable UUID userId, @NonNull ProgressPeriod period) {
        List<Session> sessions = sessionService.getByPeriod(userId, period.getStart(), period.getEnd());
        Map<Project, ProjectDuration> durations = calculateDiff(sessions);
        return mapDurationsToProgress(durations);
    }

    public Map<LocalDate, List<Progress>> getProgressByDays(@NonNull UUID userId, @NonNull ProgressPeriod period) {
        List<Session> sessions = sessionService.getByPeriod(userId, period.getStart(), period.getEnd());
        Map<LocalDate, List<Session>> groupedSessions = sessions.stream().collect(Collectors.groupingBy((s) -> s.getStartTime().toLocalDate()));

        return groupedSessions.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, s -> {
                    Map<Project, ProjectDuration> durations = calculateDiff(s.getValue());
                    return mapDurationsToProgress(durations);
                }));
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
