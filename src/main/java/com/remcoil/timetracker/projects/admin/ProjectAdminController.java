package com.remcoil.timetracker.projects.admin;

import com.remcoil.timetracker.core.MessageResponse;
import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.projects.core.domain.ProjectService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/v1/admin/projects")
@SecurityRequirement(name = "time-tracker")
public class ProjectAdminController {
    private final ProjectService projectService;

    public ProjectAdminController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public @ResponseBody Project add(@RequestBody Project project) {
        return projectService.save(project);
    }

    @PutMapping
    public Project update(@RequestBody Project project) {
        return projectService.update(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable int id, @RequestParam(defaultValue = "true") boolean isArchive) {
        if (isArchive) {
            projectService.archiveProject(id);
            return ResponseEntity.ok(new MessageResponse("Project archived"));
        }

        projectService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Project deleted"));
    }
}
