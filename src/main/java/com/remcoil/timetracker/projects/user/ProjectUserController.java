package com.remcoil.timetracker.projects.user;

import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.projects.core.domain.ProjectUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/user/projects")
@SecurityRequirement(name = "time-tracker")
public class ProjectUserController {
    private final ProjectUseCase projectUseCase;

    public ProjectUserController(ProjectUseCase projectUseCase) {
        this.projectUseCase = projectUseCase;
    }

    @GetMapping
    @ResponseBody
    public List<Project> getAll(@RequestParam(defaultValue = "false") boolean isFull) {
        return projectUseCase.getAll(isFull);
    }
}
