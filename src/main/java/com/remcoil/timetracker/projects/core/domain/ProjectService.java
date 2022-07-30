package com.remcoil.timetracker.projects.core.domain;

import com.remcoil.timetracker.projects.core.data.ProjectEntity;
import com.remcoil.timetracker.projects.core.data.ProjectRepository;
import com.remcoil.timetracker.projects.core.exceptions.ProjectAlreadyArchivedException;
import com.remcoil.timetracker.projects.core.exceptions.ProjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAll(boolean isFull) {
        List<ProjectEntity> projectEntities = isFull ?
                projectRepository.findAll() :
                projectRepository.findAllByIsArchiveFalse();

        return projectEntities.stream().map(ProjectEntity::toProject).collect(Collectors.toList());
    }

    public Project save(Project project) {
        ProjectEntity projectEntity = projectRepository.save(new ProjectEntity(project));
        return projectEntity.toProject();
    }

    public Project update(Project project) {
        ProjectEntity projectEntity = findById(project.getId());
        projectEntity.updateFields(project);
        return projectRepository.save(projectEntity).toProject();
    }

    public void archiveProject(int id) {
        ProjectEntity projectEntity = findById(id);

        if (projectEntity.isArchive()) throw new ProjectAlreadyArchivedException(id);
        projectEntity.archive();
        projectRepository.save(projectEntity);
    }

    public void delete(int id) {
        if (!projectRepository.existsById(id)) throw new ProjectNotFoundException(id);
        projectRepository.deleteById(id);
    }

    private ProjectEntity findById(int id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }
}
