package com.remcoil.timetracker.progress.core;

import com.remcoil.timetracker.progress.core.models.DailyProgress;
import com.remcoil.timetracker.progress.core.models.Progress;
import com.remcoil.timetracker.progress.core.models.ProgressPeriod;
import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.sessions.core.domain.Session;
import com.remcoil.timetracker.sessions.core.domain.SessionService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProgressService {
    private final ProgressUseCase progressUseCase;
    private final SessionService sessionService;

    public ProgressService(ProgressUseCase progressUseCase, SessionService sessionService) {
        this.progressUseCase = progressUseCase;
        this.sessionService = sessionService;
    }

    public List<DailyProgress> getTodayProgress(@NonNull UUID userId) {
        List<Progress> progresses = progressUseCase.getProgressByPeriod(userId, ProgressPeriod.day());

        Optional<Project> inWork = sessionService.getOpenedByUser(userId).map(Session::getProject);
        return progresses.stream().map(p -> {
            int inWorkId = inWork.map(Project::getId).orElse(-1);
            return new DailyProgress(p, p.getProjectId() == inWorkId);
        }).collect(Collectors.toList());
    }

    public Map<LocalDate, List<Progress>> getProgressByUser(
            @NonNull UUID userId,
            @Nullable LocalDate start,
            @Nullable LocalDate end
    ) {
        ProgressPeriod period = ProgressPeriod.orDefault(start, end);
        return progressUseCase.getProgressByDays(userId, period);
    }

    public List<Progress> getGeneralProgress(
            @Nullable UUID userId,
            @Nullable LocalDate start,
            @Nullable LocalDate end
    ) {
        ProgressPeriod period = ProgressPeriod.orDefault(start, end);
        return progressUseCase.getProgressByPeriod(userId, period);
    }
}
