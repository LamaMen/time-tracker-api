package com.remcoil.timetracker.statistics.core;

import com.remcoil.timetracker.progress.core.ProjectProgress;
import com.remcoil.timetracker.progress.core.ProjectsProgressUseCase;
import com.remcoil.timetracker.users.core.User;
import com.remcoil.timetracker.users.core.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class StatisticsService {
    private final UserService userService;
    private final ProjectsProgressUseCase projectsProgressUseCase;

    public StatisticsService(UserService userService, ProjectsProgressUseCase projectsProgressUseCase) {
        this.userService = userService;
        this.projectsProgressUseCase = projectsProgressUseCase;
    }

    public UserStatistic getStatisticByUser(UUID userId, boolean isFull) {
        User user = userService.getById(userId);
        Map<LocalDate, List<ProjectProgress>> monthProgress =
                projectsProgressUseCase.getMonthProgress(userId, isFull);

        return new UserStatistic(user, monthProgress);
    }

    public List<ProjectProgress> getGeneralStatistic(boolean isFull) {
        return projectsProgressUseCase.getGeneralProgressByMonth(isFull);
    }
}
