package com.remcoil.timetracker.sessions.core;

import com.remcoil.timetracker.projects.core.Project;
import com.remcoil.timetracker.sessions.user.OpenedSession;
import com.remcoil.timetracker.users.core.User;
import com.remcoil.timetracker.users.core.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void startWork(int projectId, User user) {
        Optional<SessionEntity> openedSession = sessionRepository.findByUserAndEndTimeIsNull(new UserEntity(user));
        if (openedSession.isPresent()) {
            SessionEntity sessionEntity = openedSession.get();

            if (sessionEntity.project.getId() == projectId)
                throw new SessionAlreadyOpenException(projectId, user.getId());

            stopSession(sessionEntity);
        }

        SessionEntity session = new SessionEntity(new Project(projectId), user);
        sessionRepository.save(session);
    }

    public void stopWork(int projectId, User user) {
        Optional<SessionEntity> openedSession = sessionRepository.findByUserAndEndTimeIsNull(new UserEntity(user));
        if (openedSession.isPresent()) {
            SessionEntity sessionEntity = openedSession.get();

            if (sessionEntity.project.getId() != projectId)
                throw new NoOpenedSessionInProjectException(projectId, user.getId());

            stopSession(sessionEntity);
        }
    }

    public OpenedSession getOpened(User user) {
        return sessionRepository.findByUserAndEndTimeIsNull(new UserEntity(user))
                .map(OpenedSession::new).orElse(null);
    }

    private void stopSession(SessionEntity openedSession) {
        openedSession.endTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        sessionRepository.save(openedSession);
    }
}
