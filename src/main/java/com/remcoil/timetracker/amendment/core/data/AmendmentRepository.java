package com.remcoil.timetracker.amendment.core.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AmendmentRepository extends JpaRepository<AmendmentEntity, Integer> {
    @Query(value = "select a from AmendmentEntity a where a.user.id = ?1 and a.amendmentDate between ?2 and ?3")
    List<AmendmentEntity> getByPeriodAndUser(UUID userId, LocalDate startTime, LocalDate endDate);

    @Query(value = "select a from AmendmentEntity a where a.amendmentDate between ?1 and ?2")
    List<AmendmentEntity> getByPeriod(LocalDate startTime, LocalDate endDate);
}
