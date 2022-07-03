package com.remcoil.timetracker.projects.core;

import com.remcoil.timetracker.projects.user.ProjectWithDuration;
import lombok.Getter;

import java.time.Duration;

@Getter
public class ProjectDuration {
    private final Integer hours;
    private final Integer minutes;

    public ProjectDuration() {
        this.hours = 0;
        this.minutes = 0;
    }

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
