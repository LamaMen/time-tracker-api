package com.remcoil.timetracker.sessions.core.data;

import com.remcoil.timetracker.projects.core.data.ProjectEntity;
import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.users.core.data.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Integer> {
    Optional<SessionEntity> findByUserAndEndTimeIsNull(UserEntity user);

    List<SessionEntity> findAllByProjectAndEndTimeIsNull(ProjectEntity project);

    List<SessionEntity> findAllByEndTimeIsNull();

    @Query(value = "select s from SessionEntity s where s.user.id = ?1 and s.startTime between ?2 and ?3")
    List<SessionEntity> getByPeriodAndUser(UUID userId, LocalDateTime startTime, LocalDateTime endDate);

    @Query(value = "select s from SessionEntity s where s.startTime between ?1 and ?2")
    List<SessionEntity> getByPeriod(LocalDateTime startTime, LocalDateTime endDate);
}
