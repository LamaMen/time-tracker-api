package com.remcoil.timetracker.projects.core;

public class ProjectAlreadyArchivedException extends RuntimeException {
    public ProjectAlreadyArchivedException(int id) {
        super("Project with id " + id + " already archived");
    }
}
