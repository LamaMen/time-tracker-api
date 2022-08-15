package com.remcoil.timetracker.sessions.core.domain;

import com.remcoil.timetracker.core.DateUtil;
import com.remcoil.timetracker.projects.core.data.ProjectEntity;
import com.remcoil.timetracker.sessions.core.data.SessionEntity;
import com.remcoil.timetracker.sessions.core.data.SessionRepository;
import com.remcoil.timetracker.users.core.data.UserEntity;
import org.springframework.lang.NonNull;
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

    public Optional<Session> getOpenedByUser(@NonNull UUID userId) {
        return sessionRepository.findByUserAndEndTimeIsNull(new UserEntity(userId)).map(SessionEntity::toSession);
    }

    public List<Session> getOpenedByProject(int projectId) {
        List<SessionEntity> sessionEntities = sessionRepository.findAllByProjectAndEndTimeIsNull(new ProjectEntity(projectId));
        return sessionEntities.stream().map(SessionEntity::toSession).collect(Collectors.toList());
    }

    public List<Session> getAllOpened() {
        List<SessionEntity> sessionEntities = sessionRepository.findAllByEndTimeIsNull();
        return sessionEntities.stream().map(SessionEntity::toSession).collect(Collectors.toList());
    }

    public List<Session> getByPeriod(@Nullable UUID userId, @NonNull LocalDateTime start, @NonNull LocalDateTime end) {
        List<SessionEntity> sessionEntities = userId != null ? sessionRepository.getByPeriodAndUser(userId, start, end) : sessionRepository.getByPeriod(start, end);

        return sessionEntities.stream().map(SessionEntity::toSession).collect(Collectors.toList());
    }

    public void startSession(int projectId, @NonNull UUID userId) {
        UserEntity user = new UserEntity(userId);
        SessionEntity session = new SessionEntity(projectId, user);
        sessionRepository.save(session);
    }

    public void stopSession(@NonNull Session session) {
        SessionEntity sessionEntity = new SessionEntity(session);
        sessionEntity.setEndTime(DateUtil.now());
        sessionRepository.save(sessionEntity);
    }
}
