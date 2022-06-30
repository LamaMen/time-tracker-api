package com.remcoil.timetracker.sessions.core;

import java.util.UUID;

public class NoOpenedSessionInProjectException extends RuntimeException {
    public NoOpenedSessionInProjectException(int projectId, UUID userId) {
        super("No opened session where project=" + projectId + " and user=" + userId);
    }
}
