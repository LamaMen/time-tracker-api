package com.remcoil.timetracker.sessions.core.domain;

import com.remcoil.timetracker.core.DateUtil;
import com.remcoil.timetracker.sessions.core.data.SessionEntity;
import com.remcoil.timetracker.sessions.core.data.SessionRepository;
import com.remcoil.timetracker.sessions.core.exceptions.NoOpenedSessionInProjectException;
import com.remcoil.timetracker.sessions.core.exceptions.SessionAlreadyOpenException;
import com.remcoil.timetracker.users.core.data.UserEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Session getOpened(UUID userId) {
        return sessionRepository.findByUserAndEndTimeIsNull(new UserEntity(userId))
                .map(SessionEntity::toSession).orElse(Session.notOpened());
    }

    public List<Session> getByPeriod(@Nullable UUID userId, LocalDateTime start, LocalDateTime end) {
        List<SessionEntity> sessionEntities = userId != null ?
                sessionRepository.getByPeriodAndUser(userId, start, end)
                : sessionRepository.getByPeriod(start, end);

        return sessionEntities.stream().map(SessionEntity::toSession).collect(Collectors.toList());
    }

    public void startWork(int projectId, UUID userId) {
        UserEntity user = new UserEntity(userId);
        Optional<SessionEntity> openedSession = sessionRepository.findByUserAndEndTimeIsNull(user);
        if (openedSession.isPresent()) {
            SessionEntity sessionEntity = openedSession.get();

            if (sessionEntity.getProject().getId() == projectId) {
                throw new SessionAlreadyOpenException(projectId, userId);
            }

            stopSession(sessionEntity);
        }

        SessionEntity session = new SessionEntity(projectId, user);
        sessionRepository.save(session);
    }

    public void stopWork(int projectId, UUID userId) {
        UserEntity user = new UserEntity(userId);
        Optional<SessionEntity> openedSession = sessionRepository.findByUserAndEndTimeIsNull(user);
        if (openedSession.isPresent()) {
            SessionEntity sessionEntity = openedSession.get();

            if (sessionEntity.getProject().getId() != projectId) {
                throw new NoOpenedSessionInProjectException(projectId, user.getId());
            }

            stopSession(sessionEntity);
        }
    }

    private void stopSession(SessionEntity openedSession) {
        openedSession.setEndTime(DateUtil.now());
        sessionRepository.save(openedSession);
    }
}
