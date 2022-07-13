package com.remcoil.timetracker.projects.core.data;

import com.remcoil.timetracker.projects.core.domain.Project;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "projects", schema = "time_tracker")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String name;
    protected boolean isArchive;

    public ProjectEntity(int id) {
        this.id = id;
        this.name = null;
        this.isArchive = false;
    }

    public ProjectEntity(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.isArchive = false;
    }

    public Project toProject() {
        return new Project(this.id, this.name);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void archive() {
        this.isArchive = true;
    }
}
