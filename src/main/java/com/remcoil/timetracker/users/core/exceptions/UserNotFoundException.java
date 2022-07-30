package com.remcoil.timetracker.users.core.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id) {
        super("No user with id = " + id.toString());
    }
}
