package com.remcoil.timetracker.progress.core.models;

import com.remcoil.timetracker.projects.core.domain.Project;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Progress {
    private final int projectId;
    private final ProjectDuration duration;

    public Progress(Project project, ProjectDuration duration) {
        this.projectId = project.getId();
        this.duration = duration;
    }
}
