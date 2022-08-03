package com.remcoil.timetracker.amendment.core.domain;

import com.remcoil.timetracker.amendment.admin.AmendmentData;
import com.remcoil.timetracker.projects.core.domain.Project;
import com.remcoil.timetracker.projects.core.domain.ProjectCrudService;
import com.remcoil.timetracker.users.core.domain.User;
import com.remcoil.timetracker.users.core.domain.UserService;
import org.springframework.stereotype.Component;

@Component
public class AmendmentUseCase {
    private final ProjectCrudService projectService;
    private final UserService userService;
    private final AmendmentService amendmentService;

    public AmendmentUseCase(ProjectCrudService projectService, UserService userService, AmendmentService amendmentService) {
        this.projectService = projectService;
        this.userService = userService;
        this.amendmentService = amendmentService;
    }

    public void add(AmendmentData amendmentData) {
        Project project = projectService.getById(amendmentData.getProjectId());
        User user = userService.getById(amendmentData.getUserId());
        Amendment amendment = amendmentData.toAmendment(user, project);
        amendmentService.add(amendment);
    }
}
