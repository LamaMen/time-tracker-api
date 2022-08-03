package com.remcoil.timetracker.projects.core.domain;

import com.remcoil.timetracker.projects.core.exceptions.ProjectAlreadyArchivedException;
import com.remcoil.timetracker.sessions.core.domain.SessionService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectUseCase {
    private final ProjectCrudService projectService;
    private final SessionService sessionService;

    public ProjectUseCase(ProjectCrudService projectService, SessionService sessionService) {
        this.projectService = projectService;
        this.sessionService = sessionService;
    }

    public List<Project> getAll(boolean isFull) {
        return projectService.getAll(isFull);
    }

    public Project save(Project project) {
        return projectService.save(project);
    }

    public Project update(Project project) {
        return projectService.update(project);
    }

    public void delete(int id, boolean isArchive) {
        if (isArchive) {
            archiveProject(id);
        } else {
            deleteProject(id);
        }
    }

    private void archiveProject(int id) {
        Project project = projectService.getById(id);
        if (project.isArchive()) throw new ProjectAlreadyArchivedException(id);
        sessionService.getOpenedByProject(id).forEach(sessionService::stopSession);
        project.archive();
        projectService.update(project);
    }

    private void deleteProject(int id) {
        Project project = projectService.getById(id);
        projectService.delete(project);
    }
}
