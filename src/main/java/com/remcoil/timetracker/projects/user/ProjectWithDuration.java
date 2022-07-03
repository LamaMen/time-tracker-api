package com.remcoil.timetracker.projects.user;

import com.remcoil.timetracker.projects.core.ProjectDuration;
import com.remcoil.timetracker.projects.core.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProjectWithDuration {
    private int id;
    private String name;
    private ProjectDuration duration;

    public ProjectWithDuration(ProjectEntity project, ProjectDuration duration) {
        this.id = project.getId();
        this.name = project.getName();
        this.duration = duration == null ? new ProjectDuration() : duration;
    }
}
