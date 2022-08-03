package com.remcoil.timetracker.progress.core.models;

import com.remcoil.timetracker.projects.core.domain.Project;

import java.time.Duration;
import java.time.LocalDate;

public interface Continuous {
    Project getProject();

    LocalDate getDate();

    Duration calculateDuration();
}
