package com.remcoil.timetracker.amendment.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.remcoil.timetracker.amendment.core.domain.Amendment;
import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.users.core.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AmendmentData {
    private long id;
    @JsonProperty("user_id")
    private UUID userId;
    @JsonProperty("project_id")
    private int projectId;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Moscow")
    private LocalDate date;
    private boolean isPositive;
    private int hours;
    private int minutes;

    public Amendment toAmendment(User user, Project project) {
        return new Amendment(id, user, project, date, isPositive, hours, minutes);
    }
}