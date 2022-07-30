package com.remcoil.timetracker.progress.core.models;

import lombok.Getter;

@Getter
public class DailyProgress extends Progress {
    private final boolean isInWork;

    public DailyProgress(Progress progress, boolean isInWork) {
        super(progress.getProjectId(), progress.getDuration());
        this.isInWork = isInWork;
    }
}
