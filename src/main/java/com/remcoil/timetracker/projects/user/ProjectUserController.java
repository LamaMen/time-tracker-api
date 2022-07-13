package com.remcoil.timetracker.projects.user;

import com.remcoil.timetracker.progress.core.ProjectsProgressUseCase;
import com.remcoil.timetracker.progress.core.ProjectProgress;
import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.projects.core.domain.ProjectService;
import com.remcoil.timetracker.users.core.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/user/projects")
@SecurityRequirement(name = "time-tracker")
public class ProjectUserController {
    private final ProjectsProgressUseCase projectsProgressUseCase;
    private final ProjectService projectService;

    public ProjectUserController(ProjectsProgressUseCase projectsProgressUseCase, ProjectService projectService) {
        this.projectsProgressUseCase = projectsProgressUseCase;
        this.projectService = projectService;
    }

    @GetMapping
    public @ResponseBody List<ProjectProgress> getAll(
            @AuthenticationPrincipal User user, @RequestParam(defaultValue = "false") boolean isFull
    ) {
        return projectsProgressUseCase.getTodayProgress(user.getId(), isFull);
    }

    @GetMapping("/in-work")
    public @ResponseBody Project getInWork(@AuthenticationPrincipal User user) {
        return projectService.getInWork(user);
    }
}
