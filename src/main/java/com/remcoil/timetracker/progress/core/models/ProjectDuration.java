package com.remcoil.timetracker.progress.core.models;

import lombok.Getter;

import java.time.Duration;

@Getter
public class ProjectDuration {
    private final int hours;
    private final int minutes;

    public ProjectDuration(Duration duration) {
        int hours = (int) duration.toHours();
        int minutes = (int) Math.ceil((duration.getSeconds() - hours * 3600) / 60.0);

        if (minutes == 60) {
            this.minutes = 0;
            this.hours = hours + 1;
        } else {
            this.minutes = minutes;
            this.hours = hours;
        }
    }

    @Override
    public String toString() {
        return "H: " + hours + " M: " + minutes;
    }
}
