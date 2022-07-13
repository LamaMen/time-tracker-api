package com.remcoil.timetracker.sessions.core;

import com.remcoil.timetracker.users.core.UserEntity;
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

    @Query(value = "select s from SessionEntity s where s.user.id = ?1 and s.startTime between ?2 and ?3")
    List<SessionEntity> getByPeriodAndUser(UUID userId, LocalDateTime startTime, LocalDateTime endDate);

    @Query(value = "select s from SessionEntity s where s.startTime between ?1 and ?2")
    List<SessionEntity> getByPeriod(LocalDateTime startTime, LocalDateTime endDate);
}
