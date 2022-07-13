package com.remcoil.timetracker.sessions.core;

import com.remcoil.timetracker.users.core.User;
import com.remcoil.timetracker.users.core.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session getOpened(User user) {
        return sessionRepository.findByUserAndEndTimeIsNull(new UserEntity(user))
                .map(SessionEntity::toSession).orElseThrow(() -> new NoOpenedSessionByUserException(user.getId()));
    }

    public List<Session> getByPeriod(UUID userId, LocalDateTime start, LocalDateTime end) {
        List<SessionEntity> sessionEntities = userId != null ?
                sessionRepository.getByPeriodAndUser(userId, start, end)
                : sessionRepository.getByPeriod(start, end);

        return sessionEntities.stream().map(SessionEntity::toSession).collect(Collectors.toList());
    }

    public void startWork(int projectId, User user) {
        Optional<SessionEntity> openedSession = sessionRepository.findByUserAndEndTimeIsNull(new UserEntity(user));
        if (openedSession.isPresent()) {
            SessionEntity sessionEntity = openedSession.get();

            if (sessionEntity.project.getId() == projectId)
                throw new SessionAlreadyOpenException(projectId, user.getId());

            stopSession(sessionEntity);
        }

        SessionEntity session = new SessionEntity(projectId, user);
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

    private void stopSession(SessionEntity openedSession) {
        openedSession.endTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        sessionRepository.save(openedSession);
    }
}
