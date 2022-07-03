package com.remcoil.timetracker.sessions.core;

import com.remcoil.timetracker.projects.core.ProjectEntity;
import com.remcoil.timetracker.users.core.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Integer> {
    Optional<SessionEntity> findByUserAndEndTimeIsNull(UserEntity user);

    List<SessionEntity> findAllByUserAndStartTimeGreaterThan(UserEntity user, LocalDateTime startTime);

    List<SessionEntity> findAllByProjectAndUserAndStartTimeGreaterThan(
            ProjectEntity project, UserEntity user, LocalDateTime startTime);
}
