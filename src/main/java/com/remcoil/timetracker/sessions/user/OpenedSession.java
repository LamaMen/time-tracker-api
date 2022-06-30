package com.remcoil.timetracker.sessions.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.remcoil.timetracker.projects.core.Project;
import com.remcoil.timetracker.sessions.core.SessionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OpenedSession {
    private long id;
    private Project project;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    public OpenedSession(SessionEntity session) {
        this.id = session.getId();
        this.project = session.getProject().toProject();
        this.startTime = session.getStartTime();
    }
}
