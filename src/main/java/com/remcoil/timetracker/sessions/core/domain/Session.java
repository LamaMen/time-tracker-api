package com.remcoil.timetracker.sessions.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.users.core.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Session {
    private final long id;
    @JsonIgnore
    private final User user;
    private final Project project;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime endTime;

    public static Session notOpened() {
        return new Session(-1, null, new Project(-1, null, false), null, null);
    }
}
