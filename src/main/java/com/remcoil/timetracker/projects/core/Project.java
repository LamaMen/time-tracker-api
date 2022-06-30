package com.remcoil.timetracker.projects.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Project {
    private int id;
    private String name;

    public Project(int id) {
        this.id = id;
    }
}
