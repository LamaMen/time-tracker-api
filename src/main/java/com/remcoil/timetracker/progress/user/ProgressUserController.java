package com.remcoil.timetracker.progress.user;

import com.remcoil.timetracker.progress.core.models.DailyProgress;
import com.remcoil.timetracker.progress.core.ProgressService;
import com.remcoil.timetracker.progress.core.models.Progress;
import com.remcoil.timetracker.users.core.domain.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @GetMapping("/own")
    public @ResponseBody Map<LocalDate, List<Progress>> ownProgress(
            @AuthenticationPrincipal User user
    ) {
        return progressService.getProgressByUser(user.getId());
    }

    @GetMapping("/general")
    public @ResponseBody List<Progress> generalProgress() {
        return progressService.getGeneralProgress();
    }
}
