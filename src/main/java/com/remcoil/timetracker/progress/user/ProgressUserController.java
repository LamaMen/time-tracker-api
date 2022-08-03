package com.remcoil.timetracker.progress.user;

import com.remcoil.timetracker.progress.core.models.DailyProgress;
import com.remcoil.timetracker.progress.core.ProgressService;
import com.remcoil.timetracker.progress.core.models.Progress;
import com.remcoil.timetracker.users.core.domain.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/v1/user/progress")
@SecurityRequirement(name = "time-tracker")
public class ProgressUserController {
    private final ProgressService progressService;

    public ProgressUserController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/today")
    public @ResponseBody List<DailyProgress> getTodayProgress(
            @AuthenticationPrincipal User user
    ) {
        return progressService.getTodayProgress(user.getId());
    }

    @GetMapping()
    public @ResponseBody Map<LocalDate, List<Progress>> getSelfProgress(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "d.MM.yyyy") LocalDate start,
            @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "d.MM.yyyy") LocalDate end
    ) {
        return progressService.getProgressByUser(user.getId(), start, end);
    }

    @GetMapping("/general")
    public @ResponseBody List<Progress> getGeneralProgress(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "d.MM.yyyy") LocalDate start,
            @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "d.MM.yyyy") LocalDate end
    ) {
        return progressService.getGeneralProgress(user.getId(), start, end);
    }
}
