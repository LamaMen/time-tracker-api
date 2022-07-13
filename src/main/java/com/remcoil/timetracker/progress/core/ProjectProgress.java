package com.remcoil.timetracker.progress.core;

import com.remcoil.timetracker.projects.core.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectProgress {
    private Project project;
    private ProjectDuration duration;
}
