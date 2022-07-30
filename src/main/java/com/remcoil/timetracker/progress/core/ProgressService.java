package com.remcoil.timetracker.progress.core;

import com.remcoil.timetracker.progress.core.models.DailyProgress;
import com.remcoil.timetracker.progress.core.models.Progress;
import com.remcoil.timetracker.sessions.core.domain.Session;
import com.remcoil.timetracker.sessions.core.domain.SessionService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
        List<Progress> progresses = progressUseCase.getTodayProgress(userId);
        Session opened = sessionService.getOpened(userId);
        return progresses.stream().map(p -> {
            int inWork = opened.getProject().getId();
            return new DailyProgress(p, p.getProjectId() == inWork);
        }).collect(Collectors.toList());
    }

    public Map<LocalDate, List<Progress>> getProgressByUser(UUID userId) {
        return progressUseCase.getMonthProgress(userId);
    }

    public List<Progress> getGeneralProgress() {
        return progressUseCase.getGeneralProgressByMonth();
    }
}
