package com.remcoil.timetracker.projects.core.domain;

import com.remcoil.timetracker.projects.core.exceptions.ProjectAlreadyArchivedException;
import com.remcoil.timetracker.sessions.core.domain.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectUseCase {
    private final Logger logger = LoggerFactory.getLogger("Projects");
    private final ProjectCrudService projectService;
    private final SessionService sessionService;

    public ProjectUseCase(ProjectCrudService projectService, SessionService sessionService) {
        this.projectService = projectService;
        this.sessionService = sessionService;
    }

    public List<Project> getAll(boolean isFull) {
        return projectService.getAll(isFull);
    }

    public Project save(@NonNull Project project) {
        Project saved = projectService.save(project);
        logger.info("Project with id: {} created", saved.getId());
        return saved;
    }

    public Project update(@NonNull Project project) {
        Project updated = projectService.update(project);
        logger.info("Project with id: {} updated", updated.getId());
        return updated;
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
        logger.info("Project with id: {} archived", id);
    }

    private void deleteProject(int id) {
        Project project = projectService.getById(id);
        projectService.delete(project);
        logger.info("Project with id: {} deleted", id);
    }
}
