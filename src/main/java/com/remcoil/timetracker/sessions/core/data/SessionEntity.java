package com.remcoil.timetracker.sessions.core.data;

import com.remcoil.timetracker.core.DateUtil;
import com.remcoil.timetracker.projects.core.data.ProjectEntity;
import com.remcoil.timetracker.sessions.core.domain.Session;
import com.remcoil.timetracker.users.core.data.UserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sessions", schema = "time_tracker")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProjectEntity project;
    @Column(name = "start_date")
    private LocalDateTime startTime;
    @Column(name = "end_date")
    private LocalDateTime endTime;

    public SessionEntity(int projectId, @NonNull UserEntity user) {
        this.user = user;
        this.project = new ProjectEntity(projectId);
        this.startTime = DateUtil.now();
    }

    public SessionEntity(@NonNull Session session) {
        this.id = session.getId();
        this.user = new UserEntity(session.getUser());
        this.project = new ProjectEntity(session.getProject());
        this.startTime = session.getStartTime();
        this.endTime = session.getEndTime();
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Session toSession() {
        return new Session(id, user.toUser(), project.toProject(), startTime, endTime);
    }
}
