package com.remcoil.timetracker.statistics.core;

import com.remcoil.timetracker.progress.core.ProjectProgress;
import com.remcoil.timetracker.users.core.User;
import com.remcoil.timetracker.users.user.PublicUser;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
public class UserStatistic {
    private final PublicUser user;
    private final Map<LocalDate, List<ProjectProgress>> statistic;

    public UserStatistic(User user, Map<LocalDate, List<ProjectProgress>> statistic) {
        this.user = new PublicUser(user);
        this.statistic = statistic;
    }
}
