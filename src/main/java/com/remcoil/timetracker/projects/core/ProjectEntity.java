package com.remcoil.timetracker.projects.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

@AllArgsConstructor
@Entity
@Getter
@Table(name = "projects", schema = "time_tracker")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    boolean isArchive;

    protected ProjectEntity() {
    }

    public ProjectEntity(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.isArchive = false;
    }

    public Project toProject() {
        return new Project(this.id, this.name);
    }

    public void update(Project project) {
        this.name = project.getName();
    }
}
