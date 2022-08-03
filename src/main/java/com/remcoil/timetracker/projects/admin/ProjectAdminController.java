package com.remcoil.timetracker.projects.admin;

import com.remcoil.timetracker.core.MessageResponse;
import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.projects.core.domain.ProjectUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/v1/admin/projects")
@SecurityRequirement(name = "time-tracker")
public class ProjectAdminController {
    private final ProjectUseCase projectUseCase;

    public ProjectAdminController(ProjectUseCase projectUseCase) {
        this.projectUseCase = projectUseCase;
    }

    @PostMapping
    @ResponseBody
    public Project add(@RequestBody Project project) {
        return projectUseCase.save(project);
    }

    @PutMapping
    @ResponseBody
    public Project update(@RequestBody Project project) {
        return projectUseCase.update(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable int id, @RequestParam(defaultValue = "true") boolean isArchive) {
        projectUseCase.delete(id, isArchive);

        String message = isArchive ? "Project archived" : "Project deleted";
        return ResponseEntity.ok(new MessageResponse(message));
    }
}
