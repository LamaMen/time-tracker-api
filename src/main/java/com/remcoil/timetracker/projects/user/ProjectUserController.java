package com.remcoil.timetracker.projects.user;

import com.remcoil.timetracker.users.core.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/user/projects")
@SecurityRequirement(name = "time-tracker")
public class ProjectUserController {
    private final TodayProjectsUseCase todayProjectsUseCase;

    public ProjectUserController(TodayProjectsUseCase todayProjectsUseCase) {
        this.todayProjectsUseCase = todayProjectsUseCase;
    }

    @GetMapping
    public @ResponseBody List<ProjectWithDuration> getAll(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "false") boolean isFull
    ) {
        return todayProjectsUseCase.getProjectsWithTodayDurations(user, isFull);
    }

    @GetMapping("/{id}")
    public @ResponseBody ProjectWithDuration getById(
            @AuthenticationPrincipal User user,
            @PathVariable int id
    ) {
        return todayProjectsUseCase.getProjectWithTodayDuration(user, id);
    }
}
