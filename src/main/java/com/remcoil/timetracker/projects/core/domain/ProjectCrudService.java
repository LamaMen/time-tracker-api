package com.remcoil.timetracker.projects.core.domain;

import com.remcoil.timetracker.projects.core.data.ProjectEntity;
import com.remcoil.timetracker.projects.core.data.ProjectRepository;
import com.remcoil.timetracker.projects.core.exceptions.ProjectNotFoundException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectCrudService {
    private final ProjectRepository projectRepository;

    public ProjectCrudService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project getById(int id) {
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id)).toProject();
    }

    public List<Project> getAll(boolean isFull) {
        List<ProjectEntity> projectEntities = isFull ? projectRepository.findAll() : projectRepository.findAllByIsArchiveFalse();

        return projectEntities.stream().map(ProjectEntity::toProject).collect(Collectors.toList());
    }

    public Project save(@NonNull Project project) {
        ProjectEntity projectEntity = projectRepository.save(new ProjectEntity(project));
        return projectEntity.toProject();
    }

    public Project update(@NonNull Project project) {
        if (!projectRepository.existsById(project.getId())) {
            throw new ProjectNotFoundException(project.getId());
        }

        ProjectEntity projectEntity = new ProjectEntity(project);
        return projectRepository.save(projectEntity).toProject();
    }

    public void delete(@NonNull Project project) {
        projectRepository.deleteById(project.getId());
    }
}
