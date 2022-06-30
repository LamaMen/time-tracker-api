package com.remcoil.timetracker.projects.core;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(int id) {
        super("No project with id = " + id);
    }
}
