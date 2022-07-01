package com.remcoil.timetracker.sessions.core;

import java.util.UUID;

public class NoOpenedSessionByUserException extends RuntimeException {
    public NoOpenedSessionByUserException(UUID id) {
        super("No opened session where user=" + id);
    }
}
