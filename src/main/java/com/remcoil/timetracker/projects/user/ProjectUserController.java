package com.remcoil.timetracker.projects.user;

import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.projects.core.domain.ProjectService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/user/projects")
@SecurityRequirement(name = "time-tracker")
public class ProjectUserController {
    private final ProjectService projectService;

    public ProjectUserController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    @ResponseBody
    public List<Project> getAll(@RequestParam(defaultValue = "false") boolean isFull) {
        return projectService.getAll(isFull);
    }
}
