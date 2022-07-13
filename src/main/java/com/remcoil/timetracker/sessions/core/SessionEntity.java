package com.remcoil.timetracker.sessions.core;

import com.remcoil.timetracker.core.DateUtil;
import com.remcoil.timetracker.projects.core.data.ProjectEntity;
import com.remcoil.timetracker.users.core.User;
import com.remcoil.timetracker.users.core.UserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sessions", schema = "time_tracker")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public SessionEntity(int projectId, User user) {
        this.user = new UserEntity(user);
        this.project = new ProjectEntity(projectId);
        this.startTime = DateUtil.now();
    }

    public Session toSession() {
        return new Session(id, user.toUser(), project.toProject(), startTime, endTime);
    }
}
