package com.remcoil.timetracker.statistics.user;

import com.remcoil.timetracker.progress.core.ProjectProgress;
import com.remcoil.timetracker.statistics.core.StatisticsService;
import com.remcoil.timetracker.statistics.core.UserStatistic;
import com.remcoil.timetracker.users.core.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/user/statistic")
@SecurityRequirement(name = "time-tracker")
public class StatisticUserController {
    private final StatisticsService statisticsService;

    public StatisticUserController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public @ResponseBody UserStatistic ownStatistics(@AuthenticationPrincipal User user) {
        return statisticsService.getStatisticByUser(user.getId(), false);
    }

    @GetMapping("/general")
    public @ResponseBody List<ProjectProgress> generalStatistic() {
        return statisticsService.getGeneralStatistic(false);
    }
}
