package com.remcoil.timetracker.projects.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {
    @Query
    List<ProjectEntity> findAllByIsArchiveFalse();
}
