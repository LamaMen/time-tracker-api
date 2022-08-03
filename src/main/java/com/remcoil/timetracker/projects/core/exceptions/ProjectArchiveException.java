package com.remcoil.timetracker.projects.core.exceptions;

public class ProjectArchiveException extends RuntimeException {
    public ProjectArchiveException(int id) {
        super("Project with id=" + id + " is archive");
    }
}
