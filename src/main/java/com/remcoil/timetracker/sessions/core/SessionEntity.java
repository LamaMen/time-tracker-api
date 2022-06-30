package com.remcoil.timetracker.sessions.core;

import com.remcoil.timetracker.projects.core.Project;
import com.remcoil.timetracker.projects.core.ProjectEntity;
import com.remcoil.timetracker.users.core.User;
import com.remcoil.timetracker.users.core.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@AllArgsConstructor
@Entity
@Getter
@Table(name = "sessions", schema = "time_tracker")
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    ProjectEntity project;
    @Column(name = "start_date")
    LocalDateTime startTime;
    @Column(name = "end_date")
    LocalDateTime endTime;

    protected SessionEntity() {
    }

    public SessionEntity(Project project, User user) {
        this.user = new UserEntity(user);
        this.project = new ProjectEntity(project);
        this.startTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
    }

    public Session toSession() {
        return new Session(id, user.toUser(), project.toProject(), startTime, endTime);
    }
}
