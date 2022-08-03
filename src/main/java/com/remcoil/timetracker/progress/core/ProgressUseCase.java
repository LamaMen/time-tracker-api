package com.remcoil.timetracker.progress.core;

import com.remcoil.timetracker.amendment.core.domain.Amendment;
import com.remcoil.timetracker.amendment.core.domain.AmendmentService;
import com.remcoil.timetracker.progress.core.models.Continuous;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProgressUseCase {
    private final SessionService sessionService;
    private final AmendmentService amendmentService;

    public ProgressUseCase(SessionService sessionService, AmendmentService amendmentService) {
        this.sessionService = sessionService;
        this.amendmentService = amendmentService;
    }

    public List<Progress> getProgressByPeriod(@Nullable UUID userId, @NonNull ProgressPeriod period) {
        List<Session> sessions = sessionService.getByPeriod(userId, period.getStart(), period.getEnd());
        List<Amendment> amendments = amendmentService.getByPeriod(userId, period.getStartDay(), period.getEndDay());
        List<Continuous> items = mergeContinuous(sessions, amendments);

        Map<Project, ProjectDuration> durations = calculateDiff(items);
        return mapDurationsToProgress(durations);
    }

    public Map<LocalDate, List<Progress>> getProgressByDays(@NonNull UUID userId, @NonNull ProgressPeriod period) {
        List<Session> sessions = sessionService.getByPeriod(userId, period.getStart(), period.getEnd());
        List<Amendment> amendments = amendmentService.getByPeriod(userId, period.getStartDay(), period.getEndDay());
        List<Continuous> items = mergeContinuous(sessions, amendments);
        Map<LocalDate, List<Continuous>> groupedSessions = items.stream().collect(Collectors.groupingBy(Continuous::getDate));

        return groupedSessions.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, s -> {
            Map<Project, ProjectDuration> durations = calculateDiff(s.getValue());
            return mapDurationsToProgress(durations);
        }));
    }

    private List<Continuous> mergeContinuous(List<Session> sessions, List<Amendment> amendments) {
        List<Continuous> items = new ArrayList<>();
        items.addAll(sessions);
        items.addAll(amendments);
        return items;
    }

    private List<Progress> mapDurationsToProgress(Map<Project, ProjectDuration> durations) {
        return durations.entrySet().stream().map(p -> new Progress(p.getKey(), p.getValue())).collect(Collectors.toList());
    }

    private Map<Project, ProjectDuration> calculateDiff(List<Continuous> sessions) {
        return sessions.stream().collect(Collectors.groupingBy(Continuous::getProject, new DurationCollector()));
    }
}
