package com.remcoil.timetracker.projects.user;

import com.remcoil.timetracker.core.DateUtil;
import com.remcoil.timetracker.projects.core.ProjectDuration;
import com.remcoil.timetracker.projects.core.ProjectEntity;
import com.remcoil.timetracker.projects.core.ProjectNotFoundException;
import com.remcoil.timetracker.projects.core.ProjectRepository;
import com.remcoil.timetracker.sessions.core.SessionEntity;
import com.remcoil.timetracker.sessions.core.SessionRepository;
import com.remcoil.timetracker.users.core.User;
import com.remcoil.timetracker.users.core.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TodayProjectsUseCase {
    private final ProjectRepository projectRepository;
    private final SessionRepository sessionRepository;

    public TodayProjectsUseCase(ProjectRepository projectRepository, SessionRepository sessionRepository) {
        this.projectRepository = projectRepository;
        this.sessionRepository = sessionRepository;
    }

    public List<ProjectWithDuration> getProjectsWithTodayDurations(User user, boolean isFull) {
        List<ProjectEntity> projectEntities = getAllProjects(isFull);
        List<SessionEntity> sessionEntities = sessionRepository
                .findAllByUserAndStartTimeGreaterThan(new UserEntity(user), DateUtil.todayNight());

        Map<ProjectEntity, ProjectDuration> durations = calculateDiff(sessionEntities);

        return projectEntities.stream().map(p -> new ProjectWithDuration(p, durations.get(p))).collect(Collectors.toList());
    }

    public ProjectWithDuration getProjectWithTodayDuration(User user, int id) {
        ProjectEntity project = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
        List<SessionEntity> sessions = sessionRepository
                .findAllByProjectAndUserAndStartTimeGreaterThan(project, new UserEntity(user), DateUtil.todayNight());

        ProjectDuration duration = sessions.stream().collect(new ProjectDurationCollector());

        return new ProjectWithDuration(project, duration);
    }

    private List<ProjectEntity> getAllProjects(boolean isFull) {
        if (isFull) {
            return projectRepository.findAll();
        } else {
            return projectRepository.findAllByIsArchiveFalse();
        }
    }

    private Map<ProjectEntity, ProjectDuration> calculateDiff(List<SessionEntity> sessions) {
        return sessions.stream().collect(Collectors.groupingBy(SessionEntity::getProject, new ProjectDurationCollector()));
    }
}
