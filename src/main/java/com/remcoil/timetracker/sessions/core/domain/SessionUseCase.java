package com.remcoil.timetracker.sessions.core.domain;

import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.projects.core.domain.ProjectCrudService;
import com.remcoil.timetracker.projects.core.exceptions.ProjectArchiveException;
import com.remcoil.timetracker.sessions.core.exceptions.NoOpenedSessionInProjectException;
import com.remcoil.timetracker.sessions.core.exceptions.SessionAlreadyOpenException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SessionUseCase {
    private final SessionService sessionService;
    private final ProjectCrudService projectService;

    public SessionUseCase(SessionService sessionService, ProjectCrudService projectService) {
        this.sessionService = sessionService;
        this.projectService = projectService;
    }

    public void startWork(int projectId, UUID userId) {
        Optional<Session> openedSession = sessionService.getOpenedByUser(userId);

        if (openedSession.isPresent()) {
            Session session = openedSession.get();

            if (session.getProject().getId() == projectId) {
                throw new SessionAlreadyOpenException(projectId, userId);
            }

            sessionService.stopSession(session);
        }

        Project project = projectService.getById(projectId);
        if (project.isArchive()) {
            throw new ProjectArchiveException(projectId);
        }

        sessionService.startSession(projectId, userId);
    }

    public void stopWork(int projectId, UUID userId) {
        Session session = sessionService.getOpenedByUser(userId).orElseThrow(() -> new NoOpenedSessionInProjectException(projectId, userId));

        if (session.getProject().getId() != projectId) {
            throw new NoOpenedSessionInProjectException(projectId, userId);
        }

        sessionService.stopSession(session);
    }
}
