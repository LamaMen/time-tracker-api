package com.remcoil.timetracker.users.core.data;

import org.springframework.lang.NonNull;

public enum UserRole {
    Employee,
    Admin;

    public @NonNull String toSecurityRole() {
        return "ROLE_" + this.name();
    }
}
