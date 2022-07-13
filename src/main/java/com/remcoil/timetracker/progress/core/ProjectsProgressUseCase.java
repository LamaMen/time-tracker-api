package com.remcoil.timetracker.progress.core;

import com.remcoil.timetracker.core.DateUtil;
import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.projects.core.domain.ProjectService;
import com.remcoil.timetracker.sessions.core.Session;
import com.remcoil.timetracker.sessions.core.SessionService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProjectsProgressUseCase {
    private final ProjectService projectService;
    private final SessionService sessionService;

    public ProjectsProgressUseCase(ProjectService projectService, SessionService sessionService) {
        this.projectService = projectService;
        this.sessionService = sessionService;
    }

    public List<ProjectProgress> getTodayProgress(UUID userId, boolean isFull) {
        return getProgressByPeriod(userId, ProgressPeriod.day(), isFull);
    }

    public List<ProjectProgress> getGeneralProgressByMonth(boolean isFull) {
        return getProgressByPeriod(null, ProgressPeriod.month(), isFull);
    }

    private List<ProjectProgress> getProgressByPeriod(UUID userId, ProgressPeriod period, boolean isFull) {
        List<Project> projects = projectService.getAll(isFull);
        List<Session> sessions = sessionService.getByPeriod(userId, period.getStart(), period.getEnd());
        Map<Project, ProjectDuration> durations = calculateDiff(sessions);
        return mergeProjectsWithProgresses(projects, durations);
    }

    public Map<LocalDate, List<ProjectProgress>> getMonthProgress(UUID userId, boolean isFull) {
        List<Project> projects = projectService.getAll(isFull);
        List<Session> sessions = sessionService.getByPeriod(userId, DateUtil.startMonth(), DateUtil.endMonth());
        Map<LocalDate, List<Session>> groupedSessions = sessions.stream()
                .collect(Collectors.groupingBy((s) -> s.getStartTime().toLocalDate()));

        Map<LocalDate, List<ProjectProgress>> progress = new HashMap<>();

        groupedSessions.forEach((d, s) -> {
            Map<Project, ProjectDuration> durations = calculateDiff(s);
            List<ProjectProgress> p = mergeProjectsWithProgresses(projects, durations);
            progress.put(d, p);
        });

        return progress;
    }

    private List<ProjectProgress> mergeProjectsWithProgresses(
            List<Project> projects, Map<Project, ProjectDuration> durations
    ) {
        List<ProjectProgress> progresses = new ArrayList<>();

        for (Project p : projects) {
            ProjectProgress progress = new ProjectProgress(p, durations.getOrDefault(p, new ProjectDuration()));
            progresses.add(progress);
        }

        return progresses;
    }

    private Map<Project, ProjectDuration> calculateDiff(List<Session> sessions) {
        return sessions.stream()
                .collect(Collectors.groupingBy(Session::getProject, new ProjectDurationCollector()));
    }
}
