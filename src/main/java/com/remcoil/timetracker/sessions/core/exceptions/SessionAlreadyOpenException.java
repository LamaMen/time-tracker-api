package com.remcoil.timetracker.sessions.core.exceptions;

import java.util.UUID;

public class SessionAlreadyOpenException extends RuntimeException {
    public SessionAlreadyOpenException(int projectId, UUID id) {
        super("Session for project=" + projectId + " already open for user=" + id);
    }
}
