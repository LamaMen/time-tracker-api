package com.remcoil.timetracker.amendment.core.data;

import com.remcoil.timetracker.amendment.core.domain.Amendment;
import com.remcoil.timetracker.projects.core.data.ProjectEntity;
import com.remcoil.timetracker.users.core.data.UserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "amendment", schema = "time_tracker")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AmendmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProjectEntity project;
    private LocalDate amendmentDate;
    private boolean isPositive;
    private int hours;
    private int minutes;

    public AmendmentEntity(@NonNull Amendment amendment) {
        this.id = amendment.getId();
        this.user = new UserEntity(amendment.getUser());
        this.project = new ProjectEntity(amendment.getProject());
        this.amendmentDate = amendment.getDate();
        this.isPositive = amendment.isPositive();
        this.hours = amendment.getHours();
        this.minutes = amendment.getMinutes();
    }

    public Amendment toAmendment() {
        return new Amendment(id, user.toUser(), project.toProject(), amendmentDate, isPositive, hours, minutes);
    }
}
