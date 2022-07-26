package com.remcoil.timetracker.statistics.admin;

import com.remcoil.timetracker.statistics.core.StatisticsService;
import com.remcoil.timetracker.statistics.core.UserStatistic;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("/api/v1/admin/statistic")
@SecurityRequirement(name = "time-tracker")
public class StatisticAdminController {
    private final StatisticsService statisticsService;

    public StatisticAdminController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/{id}")
    public @ResponseBody UserStatistic getUserStatistics(
            @PathVariable UUID id, @RequestParam(defaultValue = "false") boolean isFull
    ) {
        return statisticsService.getStatisticByUser(id, isFull);
    }
}
