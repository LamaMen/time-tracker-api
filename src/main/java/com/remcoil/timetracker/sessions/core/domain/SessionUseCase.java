package com.remcoil.timetracker.sessions.core.domain;

import com.remcoil.timetracker.core.DateUtil;
import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.projects.core.domain.ProjectCrudService;
import com.remcoil.timetracker.projects.core.exceptions.ProjectArchiveException;
import com.remcoil.timetracker.sessions.core.exceptions.NoOpenedSessionInProjectException;
import com.remcoil.timetracker.sessions.core.exceptions.SessionAlreadyOpenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class SessionUseCase {
    private static final Logger logger = LoggerFactory.getLogger(SessionUseCase.class);
    private final SessionService sessionService;
    private final ProjectCrudService projectService;

    public SessionUseCase(SessionService sessionService, ProjectCrudService projectService) {
        this.sessionService = sessionService;
        this.projectService = projectService;
    }

    public void startWork(int projectId, @NonNull UUID userId) {
        Optional<Session> openedSession = sessionService.getOpenedByUser(userId);

        if (openedSession.isPresent()) {
            Session session = openedSession.get();

            if (session.getProject().getId() == projectId) {
                throw new SessionAlreadyOpenException(projectId, userId);
            }

            sessionService.stopSession(session);
            logger.info("Session (user: {}, project: {}) stopped ", userId, session.getProject().getId());
        }

        Project project = projectService.getById(projectId);
        if (project.isArchive()) {
            throw new ProjectArchiveException(projectId);
        }

        sessionService.startSession(projectId, userId);
        logger.info("Session (user: {}, project: {}) started ", userId, projectId);
    }

    public void stopWork(int projectId, @NonNull UUID userId) {
        Session session = sessionService.getOpenedByUser(userId).orElseThrow(() -> new NoOpenedSessionInProjectException(projectId, userId));

        if (session.getProject().getId() != projectId) {
            throw new NoOpenedSessionInProjectException(projectId, userId);
        }

        sessionService.stopSession(session);
        logger.info("Session (user: {}, project: {}) stopped ", userId, projectId);
    }

    @Scheduled(cron = "${app.time.time-to-close}", zone = "${app.time.zone}")
    public void stopAllAtEndOfWorkingDay() {
        List<Session> sessions = sessionService.getAllOpened();
        sessions.forEach(sessionService::stopSession);
        logger.info("Opened sessions ({}) stopped at {}", sessions.size(), DateUtil.now());
    }
}
