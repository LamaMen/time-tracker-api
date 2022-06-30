package com.remcoil.timetracker.sessions.core;

import com.remcoil.timetracker.users.core.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Integer> {
    Optional<SessionEntity> findByUserAndEndTimeIsNull(UserEntity user);
}
